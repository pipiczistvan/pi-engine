#version 330 core

in vec4 lightFactor;
in vec2 textureCoord;

out vec4 out_color;

uniform sampler2D textureSampler;

void main(void) {
    // TEXTURE
    vec4 textureFactor = texture(textureSampler, textureCoord);

    // FINAL OUTPUT
    out_color = lightFactor * textureFactor;
}