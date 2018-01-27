#version 330 core

in vec2 vTextureCoord;

out vec4 fColor;

uniform sampler2D normalTexture;
uniform sampler2D blurTexture;
uniform sampler2D depthTexture;

float LinearizeDepth(vec2 texCoord)
{
    float zNear = 0.1;
    float zFar  = 300.0;
    float depth = texture(depthTexture, texCoord).r;
    return (2.0 * zNear) / (zFar + zNear - depth * (zFar - zNear));
}

void main(void) {
    float centerDepth = LinearizeDepth(vec2(0.5));
    float depthValue = LinearizeDepth(vTextureCoord);
    vec4 normalColor = texture(normalTexture, vTextureCoord);
    vec4 blurColor = texture(blurTexture, vTextureCoord);

    float blurFactor = pow(abs(centerDepth - depthValue), 0.2);

    fColor = mix(normalColor, blurColor, blurFactor);
}
