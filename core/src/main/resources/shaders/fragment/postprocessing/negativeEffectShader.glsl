#version 330 core

const float CONTRAST = 0.3;

in vec2 vTextureCoord;

out vec4 fColor;

uniform sampler2D textureSampler;

void main(void) {
    fColor = texture(textureSampler, vTextureCoord);
    fColor.rgb = 1.0 - fColor.rgb;
}