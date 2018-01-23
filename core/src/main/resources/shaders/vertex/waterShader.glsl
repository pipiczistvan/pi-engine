#version 330 core

const int DIRECTIONAL_LIGHT_COUNT = ${lighting.directional.light.count};
const float DIRECTIONAL_SHADOW_DISTANCE = ${lighting.directional.shadow.distance};
const float DIRECTIONAL_SHADOW_TRANSITION_DISTANCE = ${lighting.directional.shadow.transition.distance};
const float PI = 3.1415926535897932384626433832795;
const float waveLength = 10.0;
const float waveAmplitude = 0.3;

struct Fog {
    vec4 color;
    float gradient;
    float density;
};
struct DirectionalShadow {
    float enabled;
    mat4 spaceMatrix;
    int mapSize;
};

layout (location = 0) in vec3 Position;
layout (location = 4) in vec4 Indicator;

out vec4 vClipSpaceGrid;
out vec4 vClipSpaceReal;
out vec3 vToCameraVector;
flat out vec3 vNormal;
out float vVisibility;
out vec4 vShadowCoords[DIRECTIONAL_LIGHT_COUNT];
out vec4 vPosition;

uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;
uniform DirectionalShadow directionalShadows[DIRECTIONAL_LIGHT_COUNT];
uniform Fog fog;
uniform vec3 cameraPosition;
uniform float waveFactor;

float calculateVisibilityFactor(float distance) {
    float visiblity = exp(-pow(distance * fog.density, fog.gradient));
    return clamp(visiblity, 0.0, 1.0);
}

vec3 calculateNormal(vec3 vertex0, vec3 vertex1, vec3 vertex2){
	vec3 tangent = vertex1 - vertex0;
	vec3 bitangent = vertex2 - vertex0;
	return cross(tangent, bitangent);
}

float generateOffset(float x, float z, float val1, float val2){
	float radiansX = ((mod(x + z * x * val1, waveLength)/waveLength) + waveFactor * mod(x * 0.8 + z, 1.5)) * 2.0 * PI;
	float radiansZ = ((mod(val2 * (z * x + x * z), waveLength)/waveLength) + waveFactor * 2.0 * mod(x , 2.0) ) * 2.0 * PI;
	return waveAmplitude * 0.5 * (sin(radiansZ) + cos(radiansX));
}

vec4 applyDistortion(vec4 vertex){
	float xDistortion = generateOffset(vertex.x, vertex.z, 0.2, 0.1);
    float yDistortion = generateOffset(vertex.x, vertex.z, 0.1, 0.3);
    float zDistortion = generateOffset(vertex.x, vertex.z, 0.15, 0.2);
	return vertex + vec4(xDistortion, yDistortion, zDistortion, 0.0);
}

void main(void) {
    vec4 worldPosition = vec4(Position, 1.0);
    vec4 worldNeighbourPosition1 = worldPosition + vec4(Indicator.x, 0.0, Indicator.y, 0.0);
    vec4 worldNeighbourPosition2 = worldPosition + vec4(Indicator.z, 0.0, Indicator.w, 0.0);

    mat4 projectionViewMatrix = projectionMatrix * viewMatrix;

    vClipSpaceGrid = projectionViewMatrix * worldPosition;

    worldPosition = applyDistortion(worldPosition);
    worldNeighbourPosition1 = applyDistortion(worldNeighbourPosition1);
    worldNeighbourPosition2 = applyDistortion(worldNeighbourPosition2);

    vClipSpaceReal = projectionViewMatrix * worldPosition;
    gl_Position = vClipSpaceReal;

    vec4 viewPosition = viewMatrix * worldPosition;

    float distance = length(viewPosition);

    vNormal = normalize(calculateNormal(worldPosition.xyz, worldNeighbourPosition1.xyz, worldNeighbourPosition2.xyz));
    vToCameraVector = normalize(cameraPosition - worldPosition.xyz);
    vVisibility = calculateVisibilityFactor(distance);
    vPosition = worldPosition;

    distance = distance - (DIRECTIONAL_SHADOW_DISTANCE - DIRECTIONAL_SHADOW_TRANSITION_DISTANCE);
    distance = distance / DIRECTIONAL_SHADOW_TRANSITION_DISTANCE;
    for (int i = 0; i < DIRECTIONAL_LIGHT_COUNT; i++) {
        vShadowCoords[i] = directionalShadows[i].spaceMatrix * worldPosition;
        vShadowCoords[i].w = clamp(1.0 - distance, 0.0, 1.0);
    }
}
