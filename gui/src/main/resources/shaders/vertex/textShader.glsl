#version 330 core

layout (location = 0) in vec2 Position;
layout (location = 1) in vec2 TextureCoords;

out vec2 vTextureCoords;

uniform vec2 translation;

void main(void) {
	gl_Position = vec4(Position + translation * vec2(2.0, -2.0), 0.0, 1.0);
	vTextureCoords = TextureCoords;
}