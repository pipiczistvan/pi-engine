#version 330 core

layout (triangles) in;
layout (triangle_strip, max_vertices = 3) out;

in vec2 vTextureCoord[3];

out vec4 gLightFactor;
out vec2 gTextureCoord;

uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;
uniform float lightEnabled;
uniform vec3 lightColor;
uniform vec3 lightPosition;

vec4 calculateLightFactor(vec3 point1, vec3 point2, vec3 point3);

void main(void) {
    vec3 point1 = gl_in[0].gl_Position.xyz;
    vec3 point2 = gl_in[1].gl_Position.xyz;
    vec3 point3 = gl_in[2].gl_Position.xyz;

    gLightFactor = lightEnabled > 0.5 ? calculateLightFactor(point1, point2, point3) : vec4(1.0);

    for(int i = 0; i < gl_in.length(); i++)
    {
        gTextureCoord = vTextureCoord[i];
        gl_Position = projectionMatrix * viewMatrix * gl_in[i].gl_Position;
        EmitVertex();
    }
    EndPrimitive();
}

vec4 calculateLightFactor(vec3 point1, vec3 point2, vec3 point3) {
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

    return vec4(diffuse, 1.0);
}