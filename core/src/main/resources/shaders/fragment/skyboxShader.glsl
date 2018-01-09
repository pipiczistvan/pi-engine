#version 330

in vec3 vTextureCoords;

out vec4 fColor;

uniform samplerCube cubeMap;

void main(void) {
    fColor = texture(cubeMap, vTextureCoords);
}