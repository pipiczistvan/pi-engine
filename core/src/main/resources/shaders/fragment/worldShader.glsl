#version 330 core

flat in vec4 vColor;
in vec2 vTextureCoord;
in float vVisibility;

out vec4 fColor;

uniform sampler2D textureSampler;
uniform float textureEnabled;
uniform vec4 color;
uniform vec4 fogColor;

void main(void) {
    // TEXTURE
    vec4 textureFactor = textureEnabled > 0.5 ? texture(textureSampler, vTextureCoord) : vec4(1.0);

    // FINAL OUTPUT
    fColor = vColor * textureFactor * color;
    fColor = mix(fogColor, fColor, vVisibility);
}