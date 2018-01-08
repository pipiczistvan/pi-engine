#version 330 core

flat in vec4 vColor;
in float vVisibility;

out vec4 fColor;

uniform vec4 color;
uniform vec4 fogColor;

void main(void) {
    // FINAL OUTPUT
    fColor = vColor * color;
    fColor = mix(fogColor, fColor, vVisibility);
}