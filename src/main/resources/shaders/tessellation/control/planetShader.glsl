#version 400 core

layout (vertices = 3) out;

in vec3 vPosition[];

out vec3 tcPosition[];

#define ID gl_InvocationID

void main(void) {
    tcPosition[ID] = vPosition[ID];
    if (ID == 0) {
        gl_TessLevelInner[0] = 6;
        gl_TessLevelOuter[0] = 6;
        gl_TessLevelOuter[1] = 6;
        gl_TessLevelOuter[2] = 6;
    }
}