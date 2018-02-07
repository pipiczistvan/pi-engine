#version 330 core

in vec2 vTextureCoord;

out vec4 fColor;

uniform vec4 color;
uniform sampler2D fontAtlas;
uniform float width;
uniform float edge;
uniform float borderWidth;
uniform float borderEdge;
uniform vec4 outlineColor;
uniform vec2 offset;

void main(void){
    float distance = 1.0 - texture(fontAtlas, vTextureCoord).a;
    float alpha = 1.0 - smoothstep(width, width + edge, distance);

    float distance2 = 1.0 - texture(fontAtlas, vTextureCoord + offset).a;
    float outlineAlpha = 1.0 - smoothstep(borderWidth, borderWidth + borderEdge, distance2);

    float overallAlpha = alpha + (1.0 - alpha) * outlineAlpha;
    vec3 overallColor = mix(outlineColor.rgb, color.xyz, alpha / overallAlpha) * color.w;

    fColor = vec4(overallColor, overallAlpha);
}
