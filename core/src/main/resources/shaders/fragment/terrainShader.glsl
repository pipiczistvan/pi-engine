#version 330 core

in vec4 gLightFactor;

out vec4 fColor;

uniform vec4 color;

void main(void) {
    // FINAL OUTPUT
    fColor = gLightFactor * color;
}