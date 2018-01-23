#version 330 core

struct Fog {
    vec4 color;
    float gradient;
    float density;
};

flat in vec4 vColor;
in vec2 vTextureCoord;
in float vVisibility;

out vec4 fColor;

uniform sampler2D textureSampler;
uniform Fog fog;

void main(void) {
	// TEXTURE
    vec4 textureFactor = texture(textureSampler, vTextureCoord);

    // FINAL OUTPUT
    fColor = vColor * textureFactor;
    fColor = mix(fog.color, fColor, vVisibility);
}
