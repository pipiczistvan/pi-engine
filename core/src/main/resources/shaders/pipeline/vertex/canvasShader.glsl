#version 330 core

layout (location = 0) in vec2 Position;

out vec2 vTextureCoord;

uniform mat4 modelMatrix;

void main(void) {
    vec2 textureCoords = Position + vec2(0.5, 0.5);
    textureCoords.y = 1.0 - textureCoords.y;
    gl_Position = modelMatrix * vec4(Position, 0.0, 1.0);
    vTextureCoord = textureCoords;
}
