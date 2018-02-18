#version 330 core

in vec2 vTextureCoord;

out vec4 fColor;

uniform sampler2D textureSampler;

void main(void) {
    fColor = texture(textureSampler, vTextureCoord);
    float radialGradient = length(vTextureCoord - vec2(-0.5)) / 1.5;

    fColor.rgb = mix(fColor.rgb, vec3(0.0), radialGradient);
}
