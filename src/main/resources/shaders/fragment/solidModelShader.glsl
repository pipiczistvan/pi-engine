#version 330 core

in vec4 gLightFactor;
in vec2 gTextureCoord;

out vec4 fColor;

uniform sampler2D textureSampler;

void main(void) {
    // TEXTURE
    vec4 textureFactor = texture(textureSampler, gTextureCoord);

    // FINAL OUTPUT
    fColor = gLightFactor * textureFactor;
}