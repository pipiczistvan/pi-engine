#version 330 core

layout (location = 0) in vec3 Position;
layout (location = 2) in vec3 Color;

out vec3 vColor;

uniform mat4 modelMatrix;

void main(void) {
    gl_Position = modelMatrix * vec4(Position, 1.0);
    vColor = Color;
}