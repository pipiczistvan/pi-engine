#version 330 core

const vec3 waterColor = vec3(0.604, 0.867, 0.851);
const float fresnelReflective = 0.9;

in vec4 vClipSpace;
in vec3 vToCameraVector;
in vec3 vNormal;

out vec4 fColor;

uniform sampler2D reflectionTexture;
uniform sampler2D refractionTexture;

float calculateFresnel(vec3 toCameraVector, vec3 normalVector) {
    vec3 viewVector = normalize(toCameraVector);
    vec3 normal = normalize(normalVector);
    float refractiveFactor = dot(viewVector, normal);
    refractiveFactor = pow(refractiveFactor, fresnelReflective);
    return clamp(refractiveFactor, 0.0, 1.0);
}

vec2 clipSpaceToTextureCoords(vec4 clipSpace) {
    vec2 normalisedDiviceSpace = (clipSpace.xy / clipSpace.w);
    return normalisedDiviceSpace / 2.0 + 0.5;
}

void main(void) {
    vec2 projectiveTextureCoords = clipSpaceToTextureCoords(vClipSpace);

    vec2 reflectTextureCoords = vec2(projectiveTextureCoords.x, -projectiveTextureCoords.y);
    vec2 refractTextureCoords = vec2(projectiveTextureCoords.x, projectiveTextureCoords.y);

    vec3 reflectColor = texture(reflectionTexture, reflectTextureCoords).rgb;
    vec3 refractColor = texture(refractionTexture, refractTextureCoords).rgb;

    float fresnelFactor = calculateFresnel(vToCameraVector, vNormal);

    vec3 finalColor = mix(reflectColor, refractColor, fresnelFactor);

    fColor = vec4(finalColor, 1.0);
}