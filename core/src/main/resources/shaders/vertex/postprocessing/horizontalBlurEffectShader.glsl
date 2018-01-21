#version 330 core

const int KERNEL_SIZE = 11;

layout (location = 0) in vec3 Position;

out vec2 vBlurTextureCoords[KERNEL_SIZE];

uniform float textureWidth;

void main(void) {
    gl_Position = vec4(Position, 1.0);
    vec2 centerTextureCoords = Position.xy * 0.5 + 0.5;
    float pixelSize = 1.0 / textureWidth;

    for (int i = -5; i <= 5; i++) {
        vBlurTextureCoords[i + 5] = centerTextureCoords + vec2(pixelSize * i, 0.0);
    }
}