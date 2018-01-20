#version 330 core

in vec2 vTextureCoord;

out vec4 fColor;

uniform sampler2D textureSampler;
uniform vec4 color;

void main(void) {
    // TEXTURE
    vec4 textureFactor = texture(textureSampler, vTextureCoord);

    // FINAL OUTPUT
    fColor = textureFactor * color;
}