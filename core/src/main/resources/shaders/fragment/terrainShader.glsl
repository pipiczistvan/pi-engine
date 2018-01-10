#version 330 core

flat in vec4 vColor;
in float vVisibility;
in vec4 vShadowCoords;

out vec4 fColor;

uniform vec4 color;
uniform vec4 fogColor;
uniform sampler2D shadowMap;

void main(void) {
    float objectNearestToLight = texture(shadowMap, vShadowCoords.xy).r;
    float lightFactor = 1.0;
    if (vShadowCoords.z > objectNearestToLight + 0.0002) {
        lightFactor = 1.0 - (vShadowCoords.w * 0.6);
    }

    // FINAL OUTPUT
    fColor = vColor * color * lightFactor;
    fColor = mix(fogColor, fColor, vVisibility);
}