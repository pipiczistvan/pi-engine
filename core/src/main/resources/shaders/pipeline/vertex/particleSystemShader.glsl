#version 330 core

layout (location = 0) in vec2 Position;

uniform mat4 projectionMatrix;
uniform mat4 modelViewMatrix;

void main(void) {
	gl_Position = projectionMatrix * modelViewMatrix * vec4(Position, 0.0, 1.0);
}
