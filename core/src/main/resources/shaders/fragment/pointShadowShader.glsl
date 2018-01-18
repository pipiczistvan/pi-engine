#version 330 core

in vec4 gPosition;

uniform vec3 lightPosition;
uniform float farPlane;

void main() {
    float lightDistance = length(gPosition.xyz - lightPosition);
    lightDistance = lightDistance / farPlane;

    gl_FragDepth = lightDistance;
}
