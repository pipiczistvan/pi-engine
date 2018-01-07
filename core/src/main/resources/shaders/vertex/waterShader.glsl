#version 330 core

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

uniform mat4 modelMatrix;
uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;
uniform vec3 cameraPosition;
uniform float waveTime;
uniform vec3 lightPosition;
uniform vec3 lightColor;
uniform vec2 lightBias;

vec3 calcSpecularLighting(vec3 toCamVector, vec3 toLightVector, vec3 normal){
	vec3 reflectedLightDirection = reflect(-toLightVector, normal);
	float specularFactor = dot(reflectedLightDirection , toCamVector);
	specularFactor = max(specularFactor, 0.0);
	specularFactor = pow(specularFactor, shineDamper);
	return specularFactor * specularReflectivity * lightColor;
}

vec3 calculateDiffuseLighting(vec3 toLightVector, vec3 normal){
	float brightness = max(dot(toLightVector, normal), 0.0);
	return (lightColor * lightBias.x) + (brightness * lightColor * lightBias.y);
}

vec3 calcNormal(vec3 vertex0, vec3 vertex1, vec3 vertex2){
	vec3 tangent = vertex1 - vertex0;
	vec3 bitangent = vertex2 - vertex0;
	return normalize(cross(tangent, bitangent));
}

float generateOffset(float x, float z, float val1, float val2){
	float radiansX = ((mod(x + z * x * val1, waveLength)/waveLength) + waveTime * mod(x * 0.8 + z, 1.5)) * 2.0 * PI;
	float radiansZ = ((mod(val2 * (z * x + x * z), waveLength)/waveLength) + waveTime * 2.0 * mod(x , 2.0) ) * 2.0 * PI;
	return waveAmplitude * 0.5 * (sin(radiansZ) + cos(radiansX));
}

vec3 applyDistortion(vec3 vertex){
	float xDistortion = generateOffset(vertex.x, vertex.z, 0.2, 0.1);
    float yDistortion = generateOffset(vertex.x, vertex.z, 0.1, 0.3);
    float zDistortion = generateOffset(vertex.x, vertex.z, 0.15, 0.2);
	return vertex + vec3(xDistortion, yDistortion, zDistortion);
}

void main(void) {
    vec3 currentVertex = vec3(Position.x, 0.0, Position.y);
    vec3 vertex1 = currentVertex + vec3(Indicator.x, 0.0, Indicator.y);
    vec3 vertex2 = currentVertex + vec3(Indicator.z, 0.0, Indicator.w);

    currentVertex = (modelMatrix * vec4(currentVertex, 1.0)).xyz;
    vertex1 = (modelMatrix * vec4(vertex1, 1.0)).xyz;
    vertex2 = (modelMatrix * vec4(vertex2, 1.0)).xyz;
    mat4 projectionViewMatrix = projectionMatrix * viewMatrix;

    vClipSpaceGrid = projectionViewMatrix * vec4(currentVertex, 1.0);

    currentVertex = applyDistortion(currentVertex);
    vertex1 = applyDistortion(vertex1);
    vertex2 = applyDistortion(vertex2);

    vClipSpaceReal = projectionViewMatrix * vec4(currentVertex, 1.0);
    gl_Position = vClipSpaceReal;

    vNormal = calcNormal(currentVertex, vertex1, vertex2);
    vToCameraVector = normalize(cameraPosition - currentVertex);
    vec3 toLightVector = normalize(lightPosition);

	vSpecular = calcSpecularLighting(vToCameraVector, toLightVector, vNormal);
    vDiffuse = calculateDiffuseLighting(toLightVector, vNormal);
}
