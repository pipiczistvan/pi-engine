#version 330 core

const int LIGHT_COUNT = ${light.count};
const float SHADOW_DISTANCE = 30.0;
const float TRANSITION_DISTANCE = 5.0;

struct Shadow {
    float enabled;
    mat4 spaceMatrix;
};

layout (location = 0) in vec3 Position;
layout (location = 2) in vec3 Color;
layout (location = 3) in vec3 Normal;

flat out vec3 vColor;
flat out vec3 vNormal;
out vec3 vPosition;
out float vVisibility;
out vec4 vShadowCoords[LIGHT_COUNT];

//////////////
// UNIFORMS //
//////////////
// TRANSFORMATION
uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;
// LIGHT
uniform Shadow shadows[LIGHT_COUNT];
// FOG
uniform float fogGradient;
uniform float fogDensity;
// CLIPPING PLANE
uniform vec4 clippingPlane;

float calculateVisibilityFactor(float distance) {
    float visiblity = exp(-pow(distance * fogDensity, fogGradient));
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

    distance = distance - (SHADOW_DISTANCE - TRANSITION_DISTANCE);
    distance = distance / TRANSITION_DISTANCE;
    for (int i = 0; i < LIGHT_COUNT; i++) {
        vShadowCoords[i] = shadows[i].spaceMatrix * worldPosition;
        vShadowCoords[i].w = clamp(1.0 - distance, 0.0, 1.0);
    }

    gl_Position = projectionMatrix * viewPosition;
}
