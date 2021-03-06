#version 330 core

#import "struct/fog";

flat in vec4 vColor;
in vec2 vTextureCoord;
in float vFogFactor;

out vec4 fColor;

uniform sampler2D textureSampler;
uniform float textureEnabled;
uniform vec4 color;
uniform Fog fog;

void main(void) {
    // TEXTURE
    vec4 textureFactor = textureEnabled > 0.5 ? texture(textureSampler, vTextureCoord) : vec4(1.0);

    // FINAL OUTPUT
    fColor = vColor * textureFactor * color;

    // FOG
    if (fog.enabled > 0.5) {
        fColor = mix(fColor, fog.color, vFogFactor);
    }
}
