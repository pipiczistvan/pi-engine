#version 330 core

const int MAX_LIGHTS = 4;
const float SHADOW_DISTANCE = 30.0;
const float TRANSITION_DISTANCE = 5.0;

struct Light {
    vec4 color;
    vec3 position;
    vec2 bias;
    vec3 attenuation;
};

const float PI = 3.1415926535897932384626433832795;

const float waveLength = 10.0;
const float waveAmplitude = 0.2;
const float specularReflectivity = 0.4;
const float shineDamper = 20.0;

layout (location = 0) in vec2 Position;
layout (location = 4) in vec4 Indicator;

out vec4 vClipSpaceGrid;
out vec4 vClipSpaceReal;
out vec3 vToCameraVector;
out vec3 vNormal;
out vec3 vSpecular;
out vec3 vDiffuse;
out float vVisibility;
out vec4 vShadowCoords;

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
// CAMERA
uniform vec3 cameraPosition;
// WAVE
uniform float waveFactor;
// SHADOW
uniform mat4 shadowMapSpaceMatrix;

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
    vec3 currentVertex = vec3(Position.x, 0.0, Position.y);
    vec3 neighbourVertex1 = currentVertex + vec3(Indicator.x, 0.0, Indicator.y);
    vec3 neighbourVertex2 = currentVertex + vec3(Indicator.z, 0.0, Indicator.w);

    vec4 worldPosition = modelMatrix * vec4(currentVertex, 1.0);
    vec4 worldNeighbourPosition1 = modelMatrix * vec4(neighbourVertex1, 1.0);
    vec4 worldNeighbourPosition2 = modelMatrix * vec4(neighbourVertex2, 1.0);

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
    vShadowCoords = shadowMapSpaceMatrix * worldPosition;

    vSpecular = vec3(0);
    vDiffuse = vec3(0);
    for (int i = 0; i < MAX_LIGHTS; i++) {
        vec3 normalizedToLightVector = normalize(lights[i].position - worldPosition.xyz);
        float attenuationFactor = calculateAttenuationFactor(lights[i], lights[i].position - worldPosition.xyz);
        vSpecular += calcSpecularLighting(lights[i], vToCameraVector, normalize(lights[i].position), vNormal) / attenuationFactor;
        vDiffuse += calculateDiffuseLighting(lights[i], normalizedToLightVector, vNormal) / attenuationFactor;
    }
    vDiffuse = max(vDiffuse, 0.1);

    distance = distance - (SHADOW_DISTANCE - TRANSITION_DISTANCE);
    distance = distance / TRANSITION_DISTANCE;
    vShadowCoords.w = clamp(1.0 - distance, 0.0, 1.0);
}
