#version 330 core

layout (location = 0) in vec2 Position;

out vec4 vClipSpace;
out vec3 vToCameraVector;
out vec3 vNormal;

uniform mat4 modelMatrix;
uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;
uniform vec3 cameraPosition;

void main(void) {
    vec4 worldPosition = modelMatrix * vec4(Position.x, 0.0, Position.y, 1.0);

    vToCameraVector = cameraPosition - worldPosition.xyz;
    vNormal = vec3(0.0, 1.0, 0.0);
    vClipSpace = projectionMatrix * viewMatrix * worldPosition;
    gl_Position = vClipSpace;
}
