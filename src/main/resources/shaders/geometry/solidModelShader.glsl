#version 330 core

layout (triangles) in;
layout (triangle_strip, max_vertices = 3) out;

in vec2 pass_textureCoord[];

out vec4 lightFactor;
out vec2 textureCoord;

uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;
uniform vec3 lightColor;
uniform vec3 lightPosition;

void main(void) {
    vec3 point1 = gl_in[0].gl_Position.xyz;
    vec3 point2 = gl_in[1].gl_Position.xyz;
    vec3 point3 = gl_in[2].gl_Position.xyz;
    vec3 center = (point1 + point2 + point3) / 3;

    vec3 toLightVector = normalize(
        lightPosition - center
    );
    vec3 normalVector = normalize(cross(
        point2 - point1,
        point3 - point1
    ));

    // LIGHT
    float nDot1 = dot(normalVector, toLightVector);
    float brightness = max(nDot1, 0.2);
    vec3 diffuse = brightness * lightColor;
    lightFactor = vec4(diffuse, 1.0);

    for(int i = 0; i < gl_in.length(); i++)
    {
        textureCoord = pass_textureCoord[i];
        gl_Position = projectionMatrix * viewMatrix * gl_in[i].gl_Position;
        EmitVertex();
    }
    EndPrimitive();
}