#version 330

const float LOWER_LIMIT = 0.0;
const float UPPER_LIMIT = 50.0;

in vec3 vTextureCoords;

out vec4 fColor;

uniform samplerCube cubeMap;
uniform vec4 fogColor;

void main(void) {
    float factor = (vTextureCoords.y - LOWER_LIMIT) / (UPPER_LIMIT - LOWER_LIMIT);
    factor = clamp(factor, 0.0, 1.0);

    fColor = texture(cubeMap, vTextureCoords);
    fColor = mix(fogColor, fColor, factor);
}