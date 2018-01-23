#version 330 core

const float SHADOW_DARKNESS = ${lighting.shadow.darkness};
const int DIRECTIONAL_LIGHT_COUNT = ${lighting.directional.light.count};
const int POINT_LIGHT_COUNT = ${lighting.point.light.count};
const int DIRECTIONAL_SHADOW_PCF_COUNT = ${lighting.directional.shadow.pcf.count};
const float TOTAL_TEXELS = (DIRECTIONAL_SHADOW_PCF_COUNT * 2.0 + 1.0) * (DIRECTIONAL_SHADOW_PCF_COUNT * 2.0 + 1.0);
const float POINT_SHADOW_DISTANCE = ${lighting.point.shadow.distance};
const float POINT_SHADOW_TRANSITION_DISTANCE = ${lighting.point.shadow.transition.distance};
const float POINT_SHADOW_TRANSITION_LENGTH = 1.0 - (POINT_SHADOW_DISTANCE - POINT_SHADOW_TRANSITION_DISTANCE) / POINT_SHADOW_DISTANCE;
const vec3 waterColor = vec3(0.604, 0.867, 0.851);
const float fresnelReflective = 0.5;
const float edgeSoftness = 2.0;
const float NEAR_PLANE = ${camera.near.plane};
const float FAR_PLANE = ${camera.far.plane};
const float minBlueness = 0.4;
const float maxBlueness = 0.75;
const float murkyDepth = 20.0;
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
struct DirectionalLight {
    float enabled;
    vec4 color;
    vec3 position;
};
struct PointLight {
    float enabled;
    vec4 color;
    vec3 position;
    vec3 attenuation;
};
struct DirectionalShadow {
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
in vec4 vShadowCoords[DIRECTIONAL_LIGHT_COUNT];
in vec4 vPosition;

out vec4 fColor;

uniform DirectionalLight directionalLights[DIRECTIONAL_LIGHT_COUNT];
uniform DirectionalShadow directionalShadows[DIRECTIONAL_LIGHT_COUNT];
uniform sampler2D directionalShadowMaps[DIRECTIONAL_LIGHT_COUNT];
uniform PointLight pointLights[POINT_LIGHT_COUNT];
uniform PointShadow pointShadows[POINT_LIGHT_COUNT];
uniform samplerCube pointShadowMaps[POINT_LIGHT_COUNT];
uniform sampler2D reflectionTexture;
uniform sampler2D refractionTexture;
uniform sampler2D depthTexture;
uniform Fog fog;
uniform vec3 cameraPosition;

float calculateDirectionalShadow(vec4 shadowCoords, sampler2D shadowMap, int mapSize) {
    float texelSize = 1.0 / mapSize;
    float total = 0.0;

    for (int x = -DIRECTIONAL_SHADOW_PCF_COUNT; x <= DIRECTIONAL_SHADOW_PCF_COUNT; x++) {
        for (int y = -DIRECTIONAL_SHADOW_PCF_COUNT; y <= DIRECTIONAL_SHADOW_PCF_COUNT; y++) {
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
    currentDepth /= POINT_SHADOW_DISTANCE;
    float viewDistance = length(viewPosition - fragPos);

    float shadow  = 0.0;
    float samples = 20;
    float diskRadius = (1.0 + (viewDistance / POINT_SHADOW_DISTANCE)) / 25.0;
    for(int i = 0; i < samples; i++) {
        float closestDepth = texture(shadowCubeMap, fragToLight + sampleOffsetDirections[i] * diskRadius).r;
        if(currentDepth > closestDepth) {
            shadow += 1.0;
        }
    }

    float strength = 1.0 - clamp(currentDepth - POINT_SHADOW_TRANSITION_LENGTH, 0.0, 1.0);

    return shadow / samples * strength;
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

float calculateAttenuationFactor(vec3 attenuation, vec3 lightPosition, vec3 vertexPosition) {
    vec3 toLightVector = lightPosition - vertexPosition;
    float distance = length(toLightVector);
    return attenuation.x + (attenuation.y * distance) + (attenuation.z * distance * distance);
}

vec3 calculateLightFactor(vec3 lightPosition, vec3 lightColor, vec3 vertexPosition, vec3 vertexNormal, float shadowFactor) {
    vec3 toLightVector = lightPosition - vertexPosition;
    float nDot1 = dot(normalize(toLightVector), vertexNormal);
    float brightness = clamp(nDot1, 0.0, 1.0 - shadowFactor);
    return brightness * lightColor;
}

vec3 calculateSpecularFactor(vec3 lightPosition, vec3 lightColor, vec3 toCamVector, vec3 vertexPosition, vec3 vertexNormal, float shadowFactor){
    vec3 toLightVector = lightPosition - vertexPosition;
    vec3 normal = normalize(toLightVector);
	vec3 reflectedLightDirection = reflect(-normal, vertexNormal);
	float specularFactor = dot(reflectedLightDirection, toCamVector);

	specularFactor = max(specularFactor, 0.0);
	specularFactor = pow(specularFactor, shineDamper);
	float brightness = 1.0 - shadowFactor;
	return specularFactor * specularReflectivity * lightColor * brightness;
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

    vec3 diffuseLight = vec3(0);
    vec3 specularLight = vec3(0);
    for(int i = 0; i < DIRECTIONAL_LIGHT_COUNT; i++) {
        if (directionalLights[i].enabled > 0.5) {
            float shadowFactor = 0;
            if (directionalShadows[i].enabled > 0.5) {
                shadowFactor += calculateDirectionalShadow(vShadowCoords[i], directionalShadowMaps[i], directionalShadows[i].mapSize);
            }
            shadowFactor = min(shadowFactor, SHADOW_DARKNESS);

            diffuseLight += calculateLightFactor(directionalLights[i].position, directionalLights[i].color.rgb, vPosition.xyz, vNormal, shadowFactor);
            specularLight += calculateSpecularFactor(directionalLights[i].position, directionalLights[i].color.rgb, vToCameraVector, vPosition.xyz, vNormal, shadowFactor);
        }
    }
    for (int i = 0; i < POINT_LIGHT_COUNT; i++) {
        if (pointLights[i].enabled > 0.5) {
            float shadowFactor = 0;
            if (pointShadows[i].enabled > 0.5) {
                shadowFactor += calculatePointShadow(vPosition.xyz, cameraPosition, pointShadows[i].position, pointShadowMaps[i]);
            }
            shadowFactor = min(shadowFactor, SHADOW_DARKNESS);

            float attenuationFactor = calculateAttenuationFactor(pointLights[i].attenuation, pointLights[i].position, vPosition.xyz);
            diffuseLight += calculateLightFactor(pointLights[i].position, pointLights[i].color.rgb, vPosition.xyz, vNormal, shadowFactor) / attenuationFactor;
            specularLight += calculateSpecularFactor(directionalLights[i].position, directionalLights[i].color.rgb, vToCameraVector, vPosition.xyz, vNormal, shadowFactor) / attenuationFactor;
        }
    }

    vec3 finalColor = textureColor * diffuseLight + specularLight;
    fColor = vec4(finalColor, clamp(waterDepth / edgeSoftness, 0.0, 1.0));
    fColor = mix(fog.color, fColor, vVisibility);

}