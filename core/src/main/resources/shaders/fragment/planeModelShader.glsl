#version 330 core

in vec2 vTextureCoord;

out vec4 fColor;

uniform sampler2D textureSampler;

void main(void) {
    fColor = texture(textureSampler, vTextureCoord);
}