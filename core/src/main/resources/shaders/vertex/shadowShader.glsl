#version 330 core

layout (location = 0) in vec3 Position;

uniform mat4 transformationMatrix;

void main(void) {
    gl_Position = transformationMatrix * vec4(Position, 1.0);
}