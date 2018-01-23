#version 330 core

const int DIRECTIONAL_LIGHT_COUNT = ${lighting.directional.light.count};
const int POINT_LIGHT_COUNT = ${lighting.point.light.count};

struct Fog {
    vec4 color;
    float gradient;
    float density;
};
struct DirectionalLight {
    float enabled;
    vec4 color;
    vec3 position;
};
struct PointLight {
    float enabled;
    vec4 color;
    vec3 position;
    vec3 attenuation;
};

layout (location = 0) in vec3 Position;
layout (location = 1) in vec2 TextureCoord;
layout (location = 3) in vec3 Normal;

out vec2 vTextureCoord;
flat out vec4 vColor;
out float vVisibility;

uniform mat4 modelMatrix;
uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;
uniform DirectionalLight directionalLights[DIRECTIONAL_LIGHT_COUNT];
uniform PointLight pointLights[POINT_LIGHT_COUNT];
uniform Fog fog;
uniform vec4 clippingPlane;
uniform float lightEmitter;

float calculateVisibilityFactor(vec3 viewPosition) {
    float distance = length(viewPosition);
    float visiblity = exp(-pow(distance * fog.density, fog.gradient));
    return clamp(visiblity, 0.0, 1.0);
}

float calculateAttenuationFactor(vec3 attenuation, vec3 lightPosition, vec3 vertexPosition) {
    vec3 toLightVector = lightPosition - vertexPosition;
    float distance = length(toLightVector);
    return attenuation.x + (attenuation.y * distance) + (attenuation.z * distance * distance);
}

vec4 calculateLightFactor(vec3 lightPosition, vec4 lightColor, vec3 vertexPosition, vec3 vertexNormal) {
    vec3 toLightVector = lightPosition - vertexPosition;
    float nDot1 = dot(normalize(toLightVector), vertexNormal);
    float brightness = max(nDot1, 0.0);
    return brightness * lightColor;
}

void main(void) {
    vec4 worldPosition = modelMatrix * vec4(Position, 1.0);
    vec4 viewPosition = viewMatrix * worldPosition;
    vec4 worldNormal = modelMatrix * vec4(Normal, 0.0);
    gl_ClipDistance[0] = dot(worldPosition, clippingPlane);

    vec3 normalizedVertexNormal = normalize(worldNormal.xyz);

    vec4 lightFactor = vec4(0);
    if (lightEmitter > 0.5) {
        lightFactor = vec4(1);
    } else {
        for (int i = 0; i < DIRECTIONAL_LIGHT_COUNT; i++) {
            if (directionalLights[i].enabled > 0.5) {
                lightFactor += calculateLightFactor(directionalLights[i].position, directionalLights[i].color, worldPosition.xyz, normalizedVertexNormal);
            }
        }
        for (int i = 0; i < POINT_LIGHT_COUNT; i++) {
            if (pointLights[i].enabled > 0.5) {
                lightFactor += calculateLightFactor(pointLights[i].position, pointLights[i].color, worldPosition.xyz, normalizedVertexNormal) /
                        calculateAttenuationFactor(pointLights[i].attenuation, pointLights[i].position, worldPosition.xyz);
            }
        }

        lightFactor = max(lightFactor, 0.1);
    }

    vColor = lightFactor;
    vTextureCoord = TextureCoord;
    vVisibility = calculateVisibilityFactor(viewPosition.xyz);
    gl_Position = projectionMatrix * viewPosition;
}