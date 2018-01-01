#version 330 core

in vec4 gLightFactor;
in vec2 gTextureCoord;

out vec4 fColor;

uniform sampler2D textureSampler;
uniform float textureEnabled;
uniform vec4 color;

void main(void) {
    // TEXTURE
    vec4 textureFactor = textureEnabled > 0.5 ? texture(textureSampler, gTextureCoord) : vec4(1.0);

    // FINAL OUTPUT
    fColor = gLightFactor * textureFactor * color;
}