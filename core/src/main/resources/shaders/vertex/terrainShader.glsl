#version 330 core

const int MAX_LIGHTS = 4;

struct Light {
    vec4 color;
    vec3 position;
};

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
uniform Light lights[MAX_LIGHTS];
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

vec4 calculateLightFactor(Light light, vec3 vertexPosition, vec3 vertexNormal) {
    vec3 toLightVector = light.position - vertexPosition;

    float nDot1 = dot(normalize(toLightVector), vertexNormal);
    float brightness = max(nDot1, 0.0);
    return brightness * light.color;
}

void main(void) {
    vec4 worldPosition = modelMatrix * vec4(Position, 1.0);
    vec4 viewPosition = viewMatrix * worldPosition;
    vec4 worldNormal = modelMatrix * vec4(Normal, 0.0);
    gl_ClipDistance[0] = dot(worldPosition, clippingPlane);

    vec3 normalizedVertexNormal = normalize(worldNormal.xyz);
    vec4 lightFactor = vec4(0);
    for(int i = 0; i < MAX_LIGHTS; i++) {
        lightFactor += calculateLightFactor(lights[i], worldPosition.xyz, normalizedVertexNormal);
    }
    lightFactor = max(lightFactor, 0.2);

    vColor = lightFactor * vec4(Color, 1.0);
    vVisibility = calculateVisibilityFactor(viewPosition.xyz);
    gl_Position = projectionMatrix * viewPosition;
}
