#version 330 core

#import "struct/fog";

flat in vec4 vColor;
in vec2 vTextureCoord;
in float vFogFactor;

out vec4 fColor;

uniform sampler2D textureSampler;
uniform Fog fog;

void main(void) {
	// TEXTURE
    vec4 textureFactor = texture(textureSampler, vTextureCoord);

    // FINAL OUTPUT
    fColor = vColor * textureFactor;

    // FOG
    if (fog.enabled > 0.5) {
        fColor = mix(fColor, fog.color, vFogFactor);
    }
}
