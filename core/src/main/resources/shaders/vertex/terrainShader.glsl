#version 330 core

const int DIRECTIONAL_LIGHT_COUNT = ${lighting.directional.light.count};
const float DIRECTIONAL_SHADOW_DISTANCE = ${lighting.directional.shadow.distance};
const float TRANSITION_DISTANCE = 5.0;

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
layout (location = 2) in vec3 Color;
layout (location = 3) in vec3 Normal;

flat out vec3 vColor;
flat out vec3 vNormal;
out vec3 vPosition;
out float vVisibility;
out vec4 vShadowCoords[DIRECTIONAL_LIGHT_COUNT];

uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;
uniform vec4 clippingPlane;
uniform DirectionalShadow directionalShadows[DIRECTIONAL_LIGHT_COUNT];
uniform Fog fog;

float calculateVisibilityFactor(float distance) {
    float visiblity = exp(-pow(distance * fog.density, fog.gradient));
    return clamp(visiblity, 0.0, 1.0);
}

void main(void) {
    vec4 worldPosition = vec4(Position, 1.0);
    vec4 viewPosition = viewMatrix * worldPosition;
    vec4 worldNormal = vec4(Normal, 0.0);
    gl_ClipDistance[0] = dot(worldPosition, clippingPlane);

    float distance = length(viewPosition);

    vColor = Color;
    vNormal = normalize(worldNormal.xyz);
    vPosition = worldPosition.xyz;
    vVisibility = calculateVisibilityFactor(distance);

    distance = distance - (DIRECTIONAL_SHADOW_DISTANCE - TRANSITION_DISTANCE);
    distance = distance / TRANSITION_DISTANCE;
    for (int i = 0; i < DIRECTIONAL_LIGHT_COUNT; i++) {
        vShadowCoords[i] = directionalShadows[i].spaceMatrix * worldPosition;
        vShadowCoords[i].w = clamp(1.0 - distance, 0.0, 1.0);
    }

    gl_Position = projectionMatrix * viewPosition;
}
