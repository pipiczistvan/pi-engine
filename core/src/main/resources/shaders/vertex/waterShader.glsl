#version 330 core

layout (location = 0) in vec3 Position;

out vec4 vClipSpace;

uniform mat4 modelMatrix;
uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;

void main(void) {
    vClipSpace = projectionMatrix * viewMatrix * modelMatrix * vec4(Position, 1.0);
    gl_Position = vClipSpace;
}
