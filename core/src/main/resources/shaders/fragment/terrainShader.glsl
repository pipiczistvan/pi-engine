#version 330 core

const int PCF_COUNT = 1;
const float TOTAL_TEXELS = (PCF_COUNT * 2.0 + 1.0) * (PCF_COUNT * 2.0 + 1.0);

flat in vec4 vColor;
in float vVisibility;
in vec4 vShadowCoords;

out vec4 fColor;

uniform vec4 fogColor;
uniform sampler2D shadowMap;

void main(void) {
    float mapSize = 2048.0;
    float texelSize = 1.0 / mapSize;
    float total = 0.0;

    for (int x = -PCF_COUNT; x <= PCF_COUNT; x++) {
        for (int y = -PCF_COUNT; y <= PCF_COUNT; y++) {
            float objectNearestToLight = texture(shadowMap, vShadowCoords.xy + vec2(x, y) * texelSize).r;
            if (vShadowCoords.z > objectNearestToLight) {
                total += 1.0;
            }
        }
    }
    total /= TOTAL_TEXELS;
    float lightFactor = max(1.0 - (total * vShadowCoords.w), 0.4);

    // FINAL OUTPUT
    fColor = vColor * lightFactor;
    fColor = mix(fogColor, fColor, vVisibility);
}