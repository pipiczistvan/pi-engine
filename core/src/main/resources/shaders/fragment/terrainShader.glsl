#version 330 core

const int LIGHT_COUNT = ${light.count};
const int PCF_COUNT = ${shadow.pcf.count};
const float TOTAL_TEXELS = (PCF_COUNT * 2.0 + 1.0) * (PCF_COUNT * 2.0 + 1.0);

struct Shadow {
    float enabled;
    mat4 spaceMatrix;
};

flat in vec4 vColor;
in float vVisibility;
in vec4 vShadowCoords[LIGHT_COUNT];
in vec4 vPosition;

out vec4 fColor;

uniform Shadow shadows[LIGHT_COUNT];
uniform sampler2D shadowMaps[LIGHT_COUNT];
uniform samplerCube pointShadowMap;
uniform vec3 pointShadowPosition;
uniform vec4 fogColor;

float pointShadowCalculation(vec3 fragPos) {
    vec3 fragToLight = fragPos - pointShadowPosition;
    float currentDepth = length(fragToLight);

    float closestDepth = texture(pointShadowMap, fragToLight).r;

    return 1.0 > closestDepth ? 0.6 : 0.0;
}

void main(void) {
    fColor = vColor;

    for (int i = 0; i < LIGHT_COUNT; i++) {
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
            float lightFactor = max(1.0 - (total * vShadowCoords[i].w), 0.4);
            fColor *= lightFactor;
        }
    }

    float pointShadowFactor = pointShadowCalculation(vPosition.xyz);
    fColor *= (1 - pointShadowFactor);

    // FINAL OUTPUT
    fColor = mix(fogColor, fColor, vVisibility);
}