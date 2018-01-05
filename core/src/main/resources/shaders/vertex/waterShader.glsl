#version 330 core

layout (location = 0) in vec3 Position;

out vec2 vTextureCoords;

uniform mat4 modelMatrix;
uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;

void main(void) {
    gl_Position = projectionMatrix * viewMatrix * modelMatrix * vec4(Position, 1.0);
    vTextureCoords = vec2(Position.x / 2.0 + 0.5, Position.z / 2.0 + 0.5);
}
