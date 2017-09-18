#version 400 core

layout (triangles, equal_spacing, ccw) in;

in vec3 tcPosition[];

uniform mat4 modelMatrix;

void main(void) {
    vec3 p0 = gl_TessCoord.x * tcPosition[0];
    vec3 p1 = gl_TessCoord.y * tcPosition[1];
    vec3 p2 = gl_TessCoord.z * tcPosition[2];

    gl_Position = modelMatrix * vec4(p0 + p1 + p2, 1);
}