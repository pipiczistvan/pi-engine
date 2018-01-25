#version 330

const float LOWER_LIMIT = 0.0;
const float UPPER_LIMIT = 50.0;

struct Fog {
    vec4 color;
    float enabled;
};

in vec3 vTextureCoords;

out vec4 fColor;

uniform samplerCube cubeMap;
uniform Fog fog;

void main(void) {
    float factor = (vTextureCoords.y - LOWER_LIMIT) / (UPPER_LIMIT - LOWER_LIMIT);
    factor = clamp(factor, 0.0, 1.0);

    fColor = texture(cubeMap, vTextureCoords);

    // FOG
    if (fog.enabled > 0.5) {
        fColor = mix(fog.color, fColor, factor);
    }
}