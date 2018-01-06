#version 330 core

in vec4 vClipSpace;

out vec4 fColor;

uniform sampler2D reflectionTexture;
uniform sampler2D refractionTexture;

void main(void) {
    vec2 normalisedDiviceSpace = (vClipSpace.xy / vClipSpace.w) / 2.0 + 0.5;
    vec2 reflectTextureCoords = vec2(normalisedDiviceSpace.x, -normalisedDiviceSpace.y);
    vec2 refractTextureCoords = vec2(normalisedDiviceSpace.x, normalisedDiviceSpace.y);

    vec4 reflectColor = texture(reflectionTexture, reflectTextureCoords);
    vec4 refractColor = texture(refractionTexture, refractTextureCoords);

    fColor = mix(reflectColor, refractColor, 0.5);
}