#version 330 core

in vec4 lightFactor;

out vec4 out_color;

void main(void) {
    out_color = lightFactor;
}