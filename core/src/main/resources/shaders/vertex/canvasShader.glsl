#version 330 core

layout (location = 0) in vec3 Position;
layout (location = 1) in vec2 TextureCoord;

out vec2 vTextureCoord;

uniform mat4 modelMatrix;

void main(void) {
    gl_Position = modelMatrix * vec4(Position, 1.0);
    vTextureCoord = vec2(-TextureCoord.y, -TextureCoord.x);
}