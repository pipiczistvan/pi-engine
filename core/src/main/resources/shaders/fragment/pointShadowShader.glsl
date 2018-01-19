#version 330 core

const float POINT_SHADOW_FAR_PLANE = ${point.shadow.far.plane};

in vec4 gPosition;

uniform vec3 lightPosition;

void main() {
    float lightDistance = length(gPosition.xyz - lightPosition);
    lightDistance = lightDistance / POINT_SHADOW_FAR_PLANE;

    gl_FragDepth = lightDistance;
}
