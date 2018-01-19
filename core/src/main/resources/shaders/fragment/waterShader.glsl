#version 330 core

const int LIGHT_COUNT = ${light.count};
const vec3 waterColor = vec3(0.604, 0.867, 0.851);
const float fresnelReflective = 0.5;
const float edgeSoftness = 2.0;
const float NEAR_PLANE = ${camera.near.plane};
const float FAR_PLANE = ${camera.far.plane};
const float minBlueness = 0.4;
const float maxBlueness = 0.75;
const float murkyDepth = 20.0;
const int PCF_COUNT = ${shadow.pcf.count};
const float TOTAL_TEXELS = (PCF_COUNT * 2.0 + 1.0) * (PCF_COUNT * 2.0 + 1.0);
const float POINT_SHADOW_FAR_PLANE = ${point.shadow.far.plane};
const float specularReflectivity = 0.4;
const float shineDamper = 20.0;
const vec3 sampleOffsetDirections[20] = vec3[]
(
   vec3( 1,  1,  1), vec3( 1, -1,  1), vec3(-1, -1,  1), vec3(-1,  1,  1),
   vec3( 1,  1, -1), vec3( 1, -1, -1), vec3(-1, -1, -1), vec3(-1,  1, -1),
   vec3( 1,  1,  0), vec3( 1, -1,  0), vec3(-1, -1,  0), vec3(-1,  1,  0),
   vec3( 1,  0,  1), vec3(-1,  0,  1), vec3( 1,  0, -1), vec3(-1,  0, -1),
   vec3( 0,  1,  1), vec3( 0, -1,  1), vec3( 0, -1, -1), vec3( 0,  1, -1)
);

struct Fog {
    vec4 color;
    float gradient;
    float density;
};
struct Light {
    vec4 color;
    vec3 position;
    vec3 attenuation;
};
struct Shadow {
    float enabled;
    mat4 spaceMatrix;
    int mapSize;
};
struct PointShadow {
    float enabled;
    vec3 position;
};

in vec4 vClipSpaceGrid;
in vec4 vClipSpaceReal;
in vec3 vToCameraVector;
flat in vec3 vNormal;
in float vVisibility;
in vec4 vShadowCoords[LIGHT_COUNT];
in vec4 vPosition;

out vec4 fColor;

uniform Light lights[LIGHT_COUNT];
uniform Shadow shadows[LIGHT_COUNT];
uniform sampler2D shadowMaps[LIGHT_COUNT];
uniform PointShadow pointShadows[LIGHT_COUNT];
uniform samplerCube pointShadowMaps[LIGHT_COUNT];
uniform sampler2D reflectionTexture;
uniform sampler2D refractionTexture;
uniform sampler2D depthTexture;
uniform Fog fog;
uniform vec3 cameraPosition;

float calculateShadow(vec4 shadowCoords, sampler2D shadowMap, int mapSize) {
    float texelSize = 1.0 / mapSize;
    float total = 0.0;

    for (int x = -PCF_COUNT; x <= PCF_COUNT; x++) {
        for (int y = -PCF_COUNT; y <= PCF_COUNT; y++) {
            float objectNearestToLight = texture(shadowMap, shadowCoords.xy + vec2(x, y) * texelSize).r;
            if (shadowCoords.z > objectNearestToLight) {
                total += 1.0;
            }
        }
    }
    total /= TOTAL_TEXELS;
    return total * shadowCoords.w;
}

float calculatePointShadow(vec3 fragPos, vec3 viewPosition, vec3 lightPosition, samplerCube shadowCubeMap) {
    vec3 fragToLight = fragPos - lightPosition;
    float currentDepth = length(fragToLight);
    currentDepth /= POINT_SHADOW_FAR_PLANE;
    currentDepth = clamp(currentDepth, 0.0, 1.0);
    float viewDistance = length(viewPosition - fragPos);

    float shadow  = 0.0;
    float samples = 20;
    float diskRadius = (1.0 + (viewDistance / POINT_SHADOW_FAR_PLANE)) / 25.0;
    for(int i = 0; i < samples; i++) {
        float closestDepth = texture(shadowCubeMap, fragToLight + sampleOffsetDirections[i] * diskRadius).r;
        if(currentDepth > closestDepth) {
            shadow += 1.0;
        }
    }
    return shadow / samples;
}

vec3 calculateMurkiness(vec3 refractColor, float waterDepth) {
    float murkyFactor = smoothstep(0, murkyDepth, waterDepth);
    float murkiness = minBlueness + murkyFactor * (maxBlueness - minBlueness);
    return mix(refractColor, waterColor, murkiness);
}

float calculateLinearDepth(float zDepth) {
    return 2.0 * NEAR_PLANE * FAR_PLANE / (FAR_PLANE + NEAR_PLANE - (2.0 * zDepth - 1.0) * (FAR_PLANE - NEAR_PLANE));
}

float calculateWaterDepth(vec2 textureCoords) {
    float zDepth = texture(depthTexture, textureCoords).r;
    float floorDistance = calculateLinearDepth(zDepth);
    float waterDistance = calculateLinearDepth(gl_FragCoord.z);
    return floorDistance - waterDistance;
}

float calculateFresnel(vec3 toCameraVector, vec3 normalVector) {
    vec3 viewVector = normalize(toCameraVector);
    vec3 normal = normalize(normalVector);
    float refractiveFactor = dot(viewVector, normal);
    refractiveFactor = pow(refractiveFactor, fresnelReflective);
    return clamp(refractiveFactor, 0.0, 1.0);
}

vec2 clipSpaceToTextureCoords(vec4 clipSpace) {
    vec2 normalisedDiviceSpace = (clipSpace.xy / clipSpace.w);
    vec2 texCoords = normalisedDiviceSpace / 2.0 + 0.5;
    return clamp(texCoords, 0.002, 0.998);
}

float calculateAttenuation(Light light, vec3 vertexPosition) {
    vec3 toLightVector = light.position - vertexPosition;
    float distance = length(toLightVector);
    return light.attenuation.x +
                (light.attenuation.y * distance) +
                (light.attenuation.z * distance * distance);
}

vec3 calculateDiffuseLight(Light light, vec3 vertexPosition, vec3 vertexNormal, float shadowFactor, float attenuation) {
    vec3 toLightVector = light.position - vertexPosition;
    float nDot1 = dot(normalize(toLightVector), vertexNormal);
    float brightness = clamp(nDot1, 0.0, 1.0 - shadowFactor);
    vec3 lightColor = light.color.xyz;
    return brightness * lightColor / attenuation;
}

vec3 calculateSpecularLight(Light light, vec3 toCamVector, vec3 vertexPosition, vec3 vertexNormal, float shadowFactor, float attenuation){
    vec3 toLightVector = light.position - vertexPosition;
    vec3 normal = normalize(toLightVector);
	vec3 reflectedLightDirection = reflect(-normal, vertexNormal);
	float specularFactor = dot(reflectedLightDirection, toCamVector);

	specularFactor = max(specularFactor, 0.0);
	specularFactor = pow(specularFactor, shineDamper);
	float brightness = 1.0 - shadowFactor;
	return specularFactor * specularReflectivity * light.color.xyz * brightness / attenuation;
}

void main(void) {
    vec2 texCoordsGrid = clipSpaceToTextureCoords(vClipSpaceGrid);
    vec2 texCoordsReal = clipSpaceToTextureCoords(vClipSpaceReal);

    vec2 reflectTextureCoords = vec2(texCoordsGrid.x, 1.0 - texCoordsGrid.y);
    vec2 refractTextureCoords = texCoordsGrid;

    vec3 reflectColor = texture(reflectionTexture, reflectTextureCoords).rgb;
    vec3 refractColor = texture(refractionTexture, refractTextureCoords).rgb;

    float waterDepth = calculateWaterDepth(texCoordsReal);

    refractColor = calculateMurkiness(refractColor, waterDepth);
    reflectColor = mix(reflectColor, waterColor, minBlueness);

    vec3 textureColor = mix(reflectColor, refractColor, calculateFresnel(vToCameraVector, vNormal));

    vec3 ambientLight = vec3(0);
    vec3 specularLight = vec3(0);

    for (int i = 0; i < LIGHT_COUNT; i++) {
        float shadowFactor = 0;
        if (shadows[i].enabled > 0.5) {
            shadowFactor += calculateShadow(vShadowCoords[i], shadowMaps[i], shadows[i].mapSize);
        }
        if (pointShadows[i].enabled > 0.5) {
            shadowFactor += calculatePointShadow(vPosition.xyz, cameraPosition, pointShadows[i].position, pointShadowMaps[i]);
        }
        shadowFactor = min(shadowFactor, 0.8);

        float attenuation = calculateAttenuation(lights[i], vPosition.xyz);
        ambientLight += calculateDiffuseLight(lights[i], vPosition.xyz, vNormal, shadowFactor, attenuation);
        specularLight += calculateSpecularLight(lights[i], vToCameraVector, vPosition.xyz, vNormal, shadowFactor, attenuation);
    }

    vec3 finalColor = textureColor * ambientLight + specularLight;
    fColor = vec4(finalColor, clamp(waterDepth / edgeSoftness, 0.0, 1.0));
    fColor = mix(fog.color, fColor, vVisibility);

}