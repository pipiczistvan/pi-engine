#version 330 core

layout (location = 0) in vec3 Position;
layout (location = 2) in vec3 Color;
layout (location = 3) in vec3 Normal;

flat out vec4 vColor;

uniform mat4 modelMatrix;
uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;
uniform float lightEnabled;
uniform vec3 lightColor;
uniform vec3 lightPosition;

vec3 calculateLightFactor(vec3 positionVector, vec3 normalVector);

void main(void) {
    vec4 worldPosition = modelMatrix * vec4(Position, 1.0);
    vec3 lightFactor = lightEnabled > 0.5 ? calculateLightFactor(worldPosition.xyz, Normal) : vec3(1.0);

    vColor = vec4(lightFactor * Color, 1.0);
    gl_Position = projectionMatrix * viewMatrix * worldPosition;
}

vec3 calculateLightFactor(vec3 positionVector, vec3 normalVector) {
    vec3 toLightVector = normalize(lightPosition - positionVector);

    // LIGHT
    float nDot1 = dot(normalVector, toLightVector);
    float brightness = max(nDot1, 0.2);
    return brightness * lightColor;
}