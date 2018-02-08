#version 330 core

in vec2 vTextureCoord;

out vec4 fColor;

uniform sampler2D textureSampler;
uniform vec4 color;

void main(void) {
    // TEXTURE
    vec4 textureFactor = texture(textureSampler, vTextureCoord);

    if (textureFactor.a < 0.5) {
        discard;
    }

    // FINAL OUTPUT
    fColor = textureFactor * color;
}
