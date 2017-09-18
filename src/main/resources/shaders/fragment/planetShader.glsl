#version 330 core

in vec4 gLightFactor;

out vec4 fColor;

void main(void) {
    fColor = gLightFactor;
}