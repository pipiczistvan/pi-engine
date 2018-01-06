#version 330 core

const vec3 waterColor = vec3(0.604, 0.867, 0.851);
const float fresnelReflective = 0.5;
const float edgeSoftness = 1.0;
const float nearPlane = 0.1;
const float farPlane = 1000;
const float minBlueness = 0.15;
const float maxBlueness = 0.85;
const float murkyDepth = 15;

in vec4 vClipSpace;
in vec3 vToCameraVector;
in vec3 vNormal;

out vec4 fColor;

uniform sampler2D reflectionTexture;
uniform sampler2D refractionTexture;
uniform sampler2D depthTexture;

vec3 calculateMurkiness(vec3 refractColor, float waterDepth) {
    float murkyFactor = smoothstep(0, murkyDepth, waterDepth);
    float murkiness = minBlueness + murkyFactor * (maxBlueness - minBlueness);
    return mix(refractColor, waterColor, murkiness);
}

float calculateLinearDepth(float zDepth) {
    return 2.0 * nearPlane * farPlane / (farPlane + nearPlane - (2.0 * zDepth - 1.0) * (farPlane - nearPlane));
}

float calculateWaterDepth(vec2 textureCoords) {
    float zDepth = texture(depthTexture, textureCoords).r;
    float floorDistance = calculateLinearDepth(zDepth);
    float waterDistance = calculateLinearDepth(gl_FragCoord.z);
    return floorDistance - waterDistance;
}

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
    float waterDepth = calculateWaterDepth(refractTextureCoords);

    refractColor = calculateMurkiness(refractColor, waterDepth);
    reflectColor = mix(reflectColor, waterColor, minBlueness);

    vec3 finalColor = mix(reflectColor, refractColor, fresnelFactor);

    fColor = vec4(finalColor, clamp(waterDepth / edgeSoftness, 0.0, 1.0));
}