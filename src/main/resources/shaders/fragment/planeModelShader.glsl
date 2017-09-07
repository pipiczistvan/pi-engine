#version 330 core

in vec2 pass_textureCoord;

out vec4 out_color;

uniform sampler2D textureSampler;

void main(void) {
    out_color = texture(textureSampler, pass_textureCoord);
}