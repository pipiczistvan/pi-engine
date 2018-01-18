#version 330 core

const int LIGHT_COUNT = ${light.count};
const float SHADOW_DISTANCE = 30.0;
const float TRANSITION_DISTANCE = 5.0;
const float PI = 3.1415926535897932384626433832795;
const float waveLength = 10.0;
const float waveAmplitude = 0.3;
const float specularReflectivity = 0.4;
const float shineDamper = 20.0;

struct Light {
    vec4 color;
    vec3 position;
    vec2 bias;
    vec3 attenuation;
};

struct Shadow {
    float enabled;
    mat4 spaceMatrix;
};

layout (location = 0) in vec3 Position;
layout (location = 4) in vec4 Indicator;

out vec4 vClipSpaceGrid;
out vec4 vClipSpaceReal;
out vec3 vToCameraVector;
out vec3 vNormal;
out vec3 vSpecular;
out vec3 vDiffuse;
out float vVisibility;
out vec4 vShadowCoords[LIGHT_COUNT];
out vec4 vPosition;

//////////////
// UNIFORMS //
//////////////
// TRANSFORMATION
uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;
// LIGHT
uniform Light lights[LIGHT_COUNT];
uniform Shadow shadows[LIGHT_COUNT];
// FOG
uniform float fogGradient;
uniform float fogDensity;
// CAMERA
uniform vec3 cameraPosition;
// WAVE
uniform float waveFactor;

float calculateVisibilityFactor(float distance) {
    float visiblity = exp(-pow(distance * fogDensity, fogGradient));
    return clamp(visiblity, 0.0, 1.0);
}

float calculateAttenuationFactor(Light light, vec3 toLightVector) {
    float distance = length(toLightVector);
    return light.attenuation.x + (light.attenuation.y * distance) + (light.attenuation.z * distance * distance);
}

vec3 calcSpecularLighting(Light light, vec3 toCamVector, vec3 toLightVector, vec3 normal){
	vec3 reflectedLightDirection = reflect(-toLightVector, normal);
	float specularFactor = dot(reflectedLightDirection , toCamVector);
	specularFactor = max(specularFactor, 0.0);
	specularFactor = pow(specularFactor, shineDamper);
	return specularFactor * specularReflectivity * light.color.xyz;
}

vec3 calculateDiffuseLighting(Light light, vec3 toLightVector, vec3 normal){
	float brightness = max(dot(toLightVector, normal), 0.0);
	return brightness * ((light.color.xyz * light.bias.x) + (light.color.xyz * light.bias.y));
}

vec3 calcNormal(vec3 vertex0, vec3 vertex1, vec3 vertex2){
	vec3 tangent = vertex1 - vertex0;
	vec3 bitangent = vertex2 - vertex0;
	return normalize(cross(tangent, bitangent));
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

    vNormal = calcNormal(worldPosition.xyz, worldNeighbourPosition1.xyz, worldNeighbourPosition2.xyz);
    vToCameraVector = normalize(cameraPosition - worldPosition.xyz);
    vVisibility = calculateVisibilityFactor(distance);
    vPosition = worldPosition;

    vSpecular = vec3(0);
    vDiffuse = vec3(0);
    for (int i = 0; i < LIGHT_COUNT; i++) {
        vec3 normalizedToLightVector = normalize(lights[i].position - worldPosition.xyz);
        float attenuationFactor = calculateAttenuationFactor(lights[i], lights[i].position - worldPosition.xyz);
        vSpecular += calcSpecularLighting(lights[i], vToCameraVector, normalize(lights[i].position), vNormal) / attenuationFactor;
        vDiffuse += calculateDiffuseLighting(lights[i], normalizedToLightVector, vNormal) / attenuationFactor;
    }
    vDiffuse = max(vDiffuse, 0.1);

    distance = distance - (SHADOW_DISTANCE - TRANSITION_DISTANCE);
    distance = distance / TRANSITION_DISTANCE;

    for (int i = 0; i < LIGHT_COUNT; i++) {
        vShadowCoords[i] = shadows[i].spaceMatrix * worldPosition;
        vShadowCoords[i].w = clamp(1.0 - distance, 0.0, 1.0);
    }
}
