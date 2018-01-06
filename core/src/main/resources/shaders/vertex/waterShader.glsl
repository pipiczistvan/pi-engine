#version 330 core

const float PI = 3.1415926535897932384626433832795;

const float waveLength = 10.0;
const float waveAmplitude = 1.0;

layout (location = 0) in vec2 Position;

out vec4 vClipSpaceGrid;
out vec4 vClipSpaceReal;
out vec3 vToCameraVector;
out vec3 vNormal;

uniform mat4 modelMatrix;
uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;
uniform vec3 cameraPosition;
uniform float waveTime;

float generateOffset(float x, float z){
	float radiansX = (x / waveLength + waveTime) * 2.0 * PI;
	float radiansZ = (z / waveLength + waveTime) * 2.0 * PI;
	return waveAmplitude * 0.5 * (sin(radiansZ) + cos(radiansX));
}

vec3 applyDistortion(vec3 vertex){
	float xDistortion = generateOffset(vertex.x, vertex.z);
	float yDistortion = generateOffset(vertex.x, vertex.z);
	float zDistortion = generateOffset(vertex.x, vertex.z);
	return vertex + vec3(xDistortion, yDistortion, zDistortion);
}

void main(void) {
    vec3 currentVertex = (modelMatrix * vec4(Position.x, 0.0, Position.y, 1.0)).xyz;

    mat4 projectionViewMatrix = projectionMatrix * viewMatrix;

    vClipSpaceGrid = projectionViewMatrix * vec4(currentVertex, 1.0);

    currentVertex = vec3(applyDistortion(currentVertex));

    vClipSpaceReal = projectionViewMatrix * vec4(currentVertex, 1.0);

    vNormal = vec3(0.0, 1.0, 0.0);

    vToCameraVector = cameraPosition - currentVertex;

    gl_Position = vClipSpaceReal;
}
