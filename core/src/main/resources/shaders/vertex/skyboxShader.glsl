#version 330

layout (location = 0) in vec3 Position;

out vec3 vTextureCoords;

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;

void main(void) {
	gl_Position = projectionMatrix * viewMatrix * vec4(Position, 1.0);
	vTextureCoords = Position;
}