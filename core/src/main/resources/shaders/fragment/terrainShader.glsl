#version 330 core

const int MAX_LIGHTS = 4;
const int PCF_COUNT = 1;
const float TOTAL_TEXELS = (PCF_COUNT * 2.0 + 1.0) * (PCF_COUNT * 2.0 + 1.0);

struct Shadow {
    float enabled;
    sampler2D shadowMap;
    mat4 spaceMatrix;
};

flat in vec4 vColor;
in float vVisibility;
in vec4 vShadowCoords[MAX_LIGHTS];

out vec4 fColor;

uniform Shadow shadows[MAX_LIGHTS];
uniform vec4 fogColor;

void main(void) {
    fColor = vColor;

    for (int i = 0; i < MAX_LIGHTS; i++) {
        if (shadows[i].enabled > 0.5) {
            float mapSize = 2048.0;
            float texelSize = 1.0 / mapSize;
            float total = 0.0;

            for (int x = -PCF_COUNT; x <= PCF_COUNT; x++) {
                for (int y = -PCF_COUNT; y <= PCF_COUNT; y++) {
                    float objectNearestToLight = texture(shadows[i].shadowMap, vShadowCoords[i].xy + vec2(x, y) * texelSize).r;
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

    // FINAL OUTPUT
    fColor = mix(fogColor, fColor, vVisibility);
}