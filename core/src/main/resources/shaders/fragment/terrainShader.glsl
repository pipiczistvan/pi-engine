#version 330 core

flat in vec4 vColor;

out vec4 fColor;

uniform vec4 color;

void main(void) {
    // FINAL OUTPUT
    fColor = vColor * color;
}