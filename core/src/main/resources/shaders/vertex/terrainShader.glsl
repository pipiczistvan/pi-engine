#version 330 core

const int MAX_LIGHTS = 4;
const float SHADOW_DISTANCE = 30.0;
const float TRANSITION_DISTANCE = 5.0;

struct Light {
    vec4 color;
    vec3 position;
    vec3 attenuation;
};

struct Shadow {
    float enabled;
    sampler2D shadowMap;
    mat4 spaceMatrix;
};

layout (location = 0) in vec3 Position;
layout (location = 2) in vec3 Color;
layout (location = 3) in vec3 Normal;

flat out vec4 vColor;
out float vVisibility;
out vec4 vShadowCoords[MAX_LIGHTS];

//////////////
// UNIFORMS //
//////////////
// TRANSFORMATION
uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;
// LIGHT
uniform Light lights[MAX_LIGHTS];
uniform Shadow shadows[MAX_LIGHTS];
// FOG
uniform float fogGradient;
uniform float fogDensity;
// CLIPPING PLANE
uniform vec4 clippingPlane;

float calculateVisibilityFactor(float distance) {
    float visiblity = exp(-pow(distance * fogDensity, fogGradient));
    return clamp(visiblity, 0.0, 1.0);
}

vec4 calculateLightFactor(Light light, vec3 vertexPosition, vec3 vertexNormal) {
    vec3 toLightVector = light.position - vertexPosition;
    float distance = length(toLightVector);
    float attenuationFactor = light.attenuation.x +
                (light.attenuation.y * distance) +
                (light.attenuation.z * distance * distance);

    float nDot1 = dot(normalize(toLightVector), vertexNormal);
    float brightness = max(nDot1, 0.0);
    return brightness * light.color / attenuationFactor;
}

void main(void) {
    vec4 worldPosition = vec4(Position, 1.0);
    vec4 viewPosition = viewMatrix * worldPosition;
    vec4 worldNormal = vec4(Normal, 0.0);
    gl_ClipDistance[0] = dot(worldPosition, clippingPlane);

    vec3 normalizedVertexNormal = normalize(worldNormal.xyz);
    vec4 lightFactor = vec4(0);
    for(int i = 0; i < MAX_LIGHTS; i++) {
        lightFactor += calculateLightFactor(lights[i], worldPosition.xyz, normalizedVertexNormal);
    }
    lightFactor = max(lightFactor, 0.1);

    float distance = length(viewPosition);

    vColor = lightFactor * vec4(Color, 1.0);
    vVisibility = calculateVisibilityFactor(distance);
    gl_Position = projectionMatrix * viewPosition;

    distance = distance - (SHADOW_DISTANCE - TRANSITION_DISTANCE);
    distance = distance / TRANSITION_DISTANCE;

    for (int i = 0; i < MAX_LIGHTS; i++) {
        vShadowCoords[i] = shadows[i].spaceMatrix * worldPosition;
        vShadowCoords[i].w = clamp(1.0 - distance, 0.0, 1.0);
    }
}
