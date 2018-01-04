#version 330 core

in vec3 gColor;
in vec4 gLightFactor;

out vec4 fColor;

uniform vec4 color;

void main(void) {
    // FINAL OUTPUT
    fColor = gLightFactor * vec4(gColor, 1.0) * color;
}