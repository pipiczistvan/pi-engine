#version 330 core

layout (location = 0) in vec3 Position;
layout (location = 2) in vec3 Color;
layout (location = 3) in vec3 Normal;

flat out vec4 vColor;
out float vVisibility;

//////////////
// UNIFORMS //
//////////////
// TRANSFORMATION
uniform mat4 modelMatrix;
uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;
// LIGHT
uniform float lightEnabled;
uniform vec4 lightColor;
uniform vec3 lightPosition;
// FOG
uniform float fogGradient;
uniform float fogDensity;
// CLIPPING PLANE
uniform vec4 clippingPlane;

float calculateVisibilityFactor(vec3 viewPosition) {
    float distance = length(viewPosition);
    float visiblity = exp(-pow(distance * fogDensity, fogGradient));
    return clamp(visiblity, 0.0, 1.0);
}

vec4 calculateLightFactor(vec3 positionVector, vec3 normalVector) {
    if (lightEnabled < 0.5) {
        return vec4(1.0);
    }

    vec3 toLightVector = lightPosition - positionVector;

    float nDot1 = dot(normalize(toLightVector), normalize(normalVector));
    float brightness = max(nDot1, 0.2);
    return brightness * lightColor;
}

void main(void) {
    vec4 worldPosition = modelMatrix * vec4(Position, 1.0);
    vec4 viewPosition = viewMatrix * worldPosition;
    vec4 worldNormal = modelMatrix * vec4(Normal, 0.0);
    gl_ClipDistance[0] = dot(worldPosition, clippingPlane);

    vec4 lightFactor = calculateLightFactor(worldPosition.xyz, worldNormal.xyz);

    vColor = lightFactor * vec4(Color, 1.0);
    vVisibility = calculateVisibilityFactor(viewPosition.xyz);
    gl_Position = projectionMatrix * viewPosition;
}
