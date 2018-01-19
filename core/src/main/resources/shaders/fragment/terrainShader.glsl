#version 330 core

const int LIGHT_COUNT = ${light.count};
const int PCF_COUNT = ${shadow.pcf.count};
const float TOTAL_TEXELS = (PCF_COUNT * 2.0 + 1.0) * (PCF_COUNT * 2.0 + 1.0);
const float POINT_SHADOW_FAR_PLANE = ${point.shadow.far.plane};

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

flat in vec3 vColor;
flat in vec3 vNormal;
in vec3 vPosition;
in float vVisibility;
in vec4 vShadowCoords[LIGHT_COUNT];

out vec4 fColor;

uniform Light lights[LIGHT_COUNT];
uniform Shadow shadows[LIGHT_COUNT];
uniform sampler2D shadowMaps[LIGHT_COUNT];
uniform PointShadow pointShadows[LIGHT_COUNT];
uniform samplerCube pointShadowMaps[LIGHT_COUNT];
uniform Fog fog;

float pointShadowCalculation(vec3 fragPos, vec3 lightPosition, samplerCube shadowCubeMap) {
    vec3 fragToLight = fragPos - lightPosition;
    float currentDepth = length(fragToLight);
    currentDepth /= POINT_SHADOW_FAR_PLANE;
    currentDepth = clamp(currentDepth, 0.0, 1.0);

    float closestDepth = texture(shadowCubeMap, fragToLight).r;

    return currentDepth > closestDepth ? 1.0 : 0.0;
}

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

void main(void) {
    vec3 ambientLight = vec3(0);
    for(int i = 0; i < LIGHT_COUNT; i++) {
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
            shadowFactor += total * vShadowCoords[i].w;
        }

        if (pointShadows[i].enabled > 0.5) {
            float pointShadowFactor = pointShadowCalculation(vPosition, pointShadows[i].position, pointShadowMaps[i]);
            shadowFactor += pointShadowFactor;
        }

        shadowFactor = min(shadowFactor, 0.8);

        ambientLight += calculateAmbientLight(lights[i], vPosition, vNormal, shadowFactor);
    }

    fColor = vec4(ambientLight * vColor, 1.0);
    // FINAL OUTPUT
    fColor = mix(fog.color, fColor, vVisibility);
}