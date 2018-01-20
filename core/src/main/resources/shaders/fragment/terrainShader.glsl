#version 330 core

const float SHADOW_DARKNESS = ${lighting.shadow.darkness};
const int DIRECTIONAL_LIGHT_COUNT = ${lighting.directional.light.count};
const int POINT_LIGHT_COUNT = ${lighting.point.light.count};
const int DIRECTIONAL_SHADOW_PCF_COUNT = ${lighting.directional.shadow.pcf.count};
const float TOTAL_TEXELS = (DIRECTIONAL_SHADOW_PCF_COUNT * 2.0 + 1.0) * (DIRECTIONAL_SHADOW_PCF_COUNT * 2.0 + 1.0);
const float POINT_SHADOW_DISTANCE = ${lighting.point.shadow.distance};
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

flat in vec3 vColor;
flat in vec3 vNormal;
in vec3 vPosition;
in float vVisibility;
in vec4 vShadowCoords[DIRECTIONAL_LIGHT_COUNT];

out vec4 fColor;

uniform DirectionalLight directionalLights[DIRECTIONAL_LIGHT_COUNT];
uniform DirectionalShadow directionalShadows[DIRECTIONAL_LIGHT_COUNT];
uniform sampler2D directionalShadowMaps[DIRECTIONAL_LIGHT_COUNT];
uniform PointLight pointLights[POINT_LIGHT_COUNT];
uniform PointShadow pointShadows[POINT_LIGHT_COUNT];
uniform samplerCube pointShadowMaps[POINT_LIGHT_COUNT];
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
    currentDepth = clamp(currentDepth, 0.0, 1.0);
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
    return shadow / samples;
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

void main(void) {
    vec3 diffuseLight = vec3(0);
    for(int i = 0; i < DIRECTIONAL_LIGHT_COUNT; i++) {
        if (directionalLights[i].enabled > 0.5) {
            float shadowFactor = 0;
            if (directionalShadows[i].enabled > 0.5) {
                shadowFactor += calculateDirectionalShadow(vShadowCoords[i], directionalShadowMaps[i], directionalShadows[i].mapSize);
            }
            shadowFactor = min(shadowFactor, SHADOW_DARKNESS);

            diffuseLight += calculateLightFactor(directionalLights[i].position, directionalLights[i].color.rgb, vPosition, vNormal, shadowFactor);
        }
    }
    for (int i = 0; i < POINT_LIGHT_COUNT; i++) {
        if (pointLights[i].enabled > 0.5) {
            float shadowFactor = 0;
            if (pointShadows[i].enabled > 0.5) {
                shadowFactor += calculatePointShadow(vPosition, cameraPosition, pointShadows[i].position, pointShadowMaps[i]);
            }
            shadowFactor = min(shadowFactor, SHADOW_DARKNESS);

            diffuseLight += calculateLightFactor(pointLights[i].position, pointLights[i].color.rgb, vPosition, vNormal, shadowFactor) /
                calculateAttenuationFactor(pointLights[i].attenuation, pointLights[i].position, vPosition);
        }
    }

    fColor = vec4(diffuseLight * vColor, 1.0);
    // FINAL OUTPUT
    fColor = mix(fog.color, fColor, vVisibility);
}