#version 330 core

const int LIGHT_COUNT = ${light.count};
const vec3 waterColor = vec3(0.604, 0.867, 0.851);
const float fresnelReflective = 0.5;
const float edgeSoftness = 2.0;
const float nearPlane = 0.1;
const float farPlane = 200;
const float minBlueness = 0.4;
const float maxBlueness = 0.75;
const float murkyDepth = 20.0;
const int PCF_COUNT = ${shadow.pcf.count};
const float TOTAL_TEXELS = (PCF_COUNT * 2.0 + 1.0) * (PCF_COUNT * 2.0 + 1.0);
const float POINT_SHADOW_FAR_PLANE = ${point.shadow.far.plane};
const float specularReflectivity = 0.4;
const float shineDamper = 20.0;

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

float pointShadowCalculation(vec3 fragPos, vec3 lightPosition, samplerCube shadowCubeMap) {
    vec3 fragToLight = fragPos - lightPosition;
    float currentDepth = length(fragToLight);
    currentDepth /= POINT_SHADOW_FAR_PLANE;
    currentDepth = clamp(currentDepth, 0.0, 1.0);

    float closestDepth = texture(shadowCubeMap, fragToLight).r;

    return currentDepth > closestDepth ? 1.0 : 0.0;
}

vec3 calculateMurkiness(vec3 refractColor, float waterDepth) {
    float murkyFactor = smoothstep(0, murkyDepth, waterDepth);
    float murkiness = minBlueness + murkyFactor * (maxBlueness - minBlueness);
    return mix(refractColor, waterColor, murkiness);
}

float calculateLinearDepth(float zDepth) {
    return 2.0 * nearPlane * farPlane / (farPlane + nearPlane - (2.0 * zDepth - 1.0) * (farPlane - nearPlane));
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

//todo: diffuse
vec3 calculateAmbientLight(Light light, vec3 vertexPosition, vec3 vertexNormal, float shadowFactor) {
    vec3 toLightVector = light.position - vertexPosition;
    float distance = length(toLightVector);
    float attenuationFactor = light.attenuation.x +
                (light.attenuation.y * distance) +
                (light.attenuation.z * distance * distance);

    float nDot1 = dot(normalize(toLightVector), vertexNormal);
    float brightness = clamp(nDot1, 0.0, 1.0 - shadowFactor);
    vec3 lightColor = light.color.xyz;
    return brightness * lightColor / attenuationFactor;
}

vec3 calcSpecularLighting(Light light, vec3 toCamVector, vec3 vertexPosition, vec3 vertexNormal, float shadowFactor){
    vec3 toLightVector = light.position - vertexPosition;
    vec3 normal = normalize(toLightVector);
	vec3 reflectedLightDirection = reflect(-normal, vertexNormal);
	float specularFactor = dot(reflectedLightDirection, toCamVector);
    float distance = length(toLightVector);
    float attenuationFactor = light.attenuation.x +
                (light.attenuation.y * distance) +
                (light.attenuation.z * distance * distance);

	specularFactor = max(specularFactor, 0.0);
	specularFactor = pow(specularFactor, shineDamper);
	float brightness = 1.0 - shadowFactor;
	return specularFactor * specularReflectivity * light.color.xyz * brightness / attenuationFactor;
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
            float mapSize = 2048.0;
            float texelSize = 1.0 / mapSize;
            float total = 0.0;

            for (int x = -PCF_COUNT; x <= PCF_COUNT; x++) {
                for (int y = -PCF_COUNT; y <= PCF_COUNT; y++) {
                    float objectNearestToLight = texture(shadowMaps[i], vShadowCoords[i].xy + vec2(x, y) * texelSize).r;
                    if (vShadowCoords[i].z > objectNearestToLight) {
                        total += 1.0;
                    }
                }
            }
            total /= TOTAL_TEXELS;
            shadowFactor += (total * vShadowCoords[i].w);
        }

        if (pointShadows[i].enabled > 0.5) {
            shadowFactor += pointShadowCalculation(vPosition.xyz, pointShadows[i].position, pointShadowMaps[i]);
        }

        shadowFactor = min(shadowFactor, 0.8);

        ambientLight += calculateAmbientLight(lights[i], vPosition.xyz, vNormal, shadowFactor);
        specularLight += calcSpecularLighting(lights[i], vToCameraVector, vPosition.xyz, vNormal, shadowFactor);
    }

    vec3 finalColor = textureColor * ambientLight + specularLight;
    fColor = vec4(finalColor, clamp(waterDepth / edgeSoftness, 0.0, 1.0));
    fColor = mix(fog.color, fColor, vVisibility);

}