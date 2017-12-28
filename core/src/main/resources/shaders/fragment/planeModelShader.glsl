#version 330 core

in vec2 vTextureCoord;

out vec4 fColor;

uniform sampler2D textureSampler;
uniform float textureEnabled;
uniform vec4 color;

void main(void) {
    // TEXTURE
    vec4 textureFactor = textureEnabled > 0.5 ? texture(textureSampler, vTextureCoord) : vec4(1.0);

    // FINAL OUTPUT
    fColor = textureFactor * color;
}