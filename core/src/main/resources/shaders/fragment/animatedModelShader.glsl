#version 330 core

const vec2 LIGHT_BIAS = vec2(0.7, 0.6);

in vec2 vTextureCoord;
in vec3 vNormal;

out vec4 fColor;

uniform sampler2D diffuseMap;
uniform vec3 lightDirection;

void main(void) {
	vec4 diffuseColor = texture(diffuseMap, vTextureCoord);
	vec3 unitNormal = normalize(vNormal);
	float diffuseLight = max(dot(-lightDirection, unitNormal), 0.0) * LIGHT_BIAS.x + LIGHT_BIAS.y;
	fColor = diffuseColor * diffuseLight;
}
