#version 330 core

layout (location = 0) in vec2 Position;
layout (location = 1) in vec2 TextureCoord;

out vec2 vTextureCoordsCurrent;
out vec2 vTextureCoordsNext;
out float vBlend;

uniform mat4 projectionMatrix;
uniform mat4 modelViewMatrix;
uniform vec2 textureOffsetCurrent;
uniform vec2 textureOffsetNext;
uniform vec2 textureCoordsInfo;

void main(void) {
    vec2 textureCoords = TextureCoord;
    textureCoords.y = 1.0 - textureCoords.y;
    textureCoords /= textureCoordsInfo.x;

    vTextureCoordsCurrent = textureCoords + textureOffsetCurrent;
    vTextureCoordsNext = textureCoords + textureOffsetNext;
    vBlend = textureCoordsInfo.y;

	gl_Position = projectionMatrix * modelViewMatrix * vec4(Position, 0.0, 1.0);
}
