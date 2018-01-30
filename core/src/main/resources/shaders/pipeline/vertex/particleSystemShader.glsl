#version 330 core

layout (location = 0) in vec2 Position;
layout (location = 7) in mat4 ModelViewMatrix;
layout (location = 11) in vec4 TextureOffset;
layout (location = 12) in float Blend;

out vec2 vTextureCoordsCurrent;
out vec2 vTextureCoordsNext;
out float vBlend;

uniform mat4 projectionMatrix;
uniform float numberOfRows;

void main(void) {
    vec2 textureCoords = Position + vec2(0.5, 0.5);
    textureCoords.y = 1.0 - textureCoords.y;
    textureCoords /= numberOfRows;

    vTextureCoordsCurrent = textureCoords + TextureOffset.xy;
    vTextureCoordsNext = textureCoords + TextureOffset.zw;
    vBlend = Blend;

	gl_Position = projectionMatrix * ModelViewMatrix * vec4(Position, 0.0, 1.0);
}
