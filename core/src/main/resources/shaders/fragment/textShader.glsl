#version 330 core

in vec2 vTextureCoord;

out vec4 fColor;

uniform vec4 color;
uniform sampler2D fontAtlas;

const float width = 0.5;
const float edge = 0.1;
const float borderWidth = 0.5;
const float borderEdge = 0.4;
const vec3 outlineColor = vec3(0.1, 0.6, 0.7);
const vec2 offset = vec2(0.0, 0.0);

void main(void){
    float distance = 1.0 - texture(fontAtlas, vTextureCoord).a;
    float alpha = 1.0 - smoothstep(width, width + edge, distance);

    float distance2 = 1.0 - texture(fontAtlas, vTextureCoord + offset).a;
    float outlineAlpha = 1.0 - smoothstep(borderWidth, borderWidth + borderEdge, distance2);

    float overallAlpha = alpha + (1.0 - alpha) * outlineAlpha;
    vec3 overallColor = mix(outlineColor, color.xyz, alpha / overallAlpha) * color.w;

    fColor = vec4(overallColor, overallAlpha);
}