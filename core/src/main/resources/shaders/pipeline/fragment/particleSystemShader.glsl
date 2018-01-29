#version 330 core

in vec2 vTextureCoordsCurrent;
in vec2 vTextureCoordsNext;
in float vBlend;

out vec4 fColor;

uniform sampler2D sprite;

void main(void) {
    vec4 colorCurrent = texture(sprite, vTextureCoordsCurrent);
    vec4 colorNext = texture(sprite, vTextureCoordsNext);

	fColor = mix(colorCurrent, colorNext, vBlend);
}