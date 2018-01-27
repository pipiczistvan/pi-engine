#version 330 core

const float POINT_SHADOW_DISTANCE = ${lighting.point.shadow.distance};

in vec4 gPosition;

uniform vec3 lightPosition;

void main() {
    float lightDistance = length(gPosition.xyz - lightPosition);
    lightDistance = lightDistance / POINT_SHADOW_DISTANCE;

    gl_FragDepth = lightDistance;
}
