#version 330 core

layout (location = 0) in vec3 Position;
layout (location = 1) in vec2 TextureCoord;

out vec2 vTextureCoord;
flat out vec4 vColor;

uniform mat4 modelMatrix;
uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;
uniform float lightEnabled;
uniform vec3 lightColor;
uniform vec3 lightPosition;
uniform vec4 clippingPlane;

vec3 calculateLightFactor(vec3 positionVector, vec3 normalVector);

void main(void) {
    vec4 worldPosition = modelMatrix * vec4(Position, 1.0);
    gl_ClipDistance[0] = dot(worldPosition, clippingPlane);

//todo: temporary (a normal vectort ki kéne számítani valahol)
    vec3 lightFactor = lightEnabled > 0.5 ? calculateLightFactor(worldPosition.xyz, vec3(0, 1, 0)) : vec3(1.0);

    vColor = vec4(lightFactor, 1.0);
    vTextureCoord = TextureCoord;
    gl_Position = projectionMatrix * viewMatrix * worldPosition;
}

vec3 calculateLightFactor(vec3 positionVector, vec3 normalVector) {
    vec3 toLightVector = normalize(lightPosition - positionVector);

    // LIGHT
    float nDot1 = dot(normalVector, toLightVector);
    float brightness = max(nDot1, 0.2);
    return brightness * lightColor;
}
