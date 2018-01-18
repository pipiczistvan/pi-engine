#version 330 core

const int LIGHT_COUNT = ${light.count};
const vec3 waterColor = vec3(0.604, 0.867, 0.851);
const float fresnelReflective = 0.5;
const float edgeSoftness = 2.0;
const float nearPlane = 0.1;
const float farPlane = 200;
const float minBlueness = 0.4;
const float maxBlueness = 0.75;
const float murkyDepth = 20.0;
const int PCF_COUNT = ${shadow.pcf.count};
const float TOTAL_TEXELS = (PCF_COUNT * 2.0 + 1.0) * (PCF_COUNT * 2.0 + 1.0);

struct Shadow {
    float enabled;
    mat4 spaceMatrix;
};

in vec4 vClipSpaceGrid;
in vec4 vClipSpaceReal;
in vec3 vToCameraVector;
in vec3 vNormal;
in vec3 vSpecular;
in vec3 vDiffuse;
in float vVisibility;
in vec4 vShadowCoords[LIGHT_COUNT];
in vec4 vPosition;

out vec4 fColor;

uniform Shadow shadows[LIGHT_COUNT];
uniform sampler2D shadowMaps[LIGHT_COUNT];
uniform sampler2D reflectionTexture;
uniform sampler2D refractionTexture;
uniform sampler2D depthTexture;
uniform samplerCube pointShadowMap;
uniform vec3 pointShadowPosition;
uniform vec4 fogColor;

float pointShadowCalculation(vec3 fragPos) {
    vec3 fragToLight = fragPos - pointShadowPosition;
    float currentDepth = length(fragToLight);

    float closestDepth = texture(pointShadowMap, fragToLight).r;

    return 1.0 > closestDepth ? 0.6 : 0.0;
}

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
    vec2 texCoords = normalisedDiviceSpace / 2.0 + 0.5;
    return clamp(texCoords, 0.002, 0.998);
}

void main(void) {
    vec2 texCoordsGrid = clipSpaceToTextureCoords(vClipSpaceGrid);
    vec2 texCoordsReal = clipSpaceToTextureCoords(vClipSpaceReal);

    vec2 reflectTextureCoords = vec2(texCoordsGrid.x, 1.0 - texCoordsGrid.y);
    vec2 refractTextureCoords = texCoordsGrid;

    vec3 reflectColor = texture(reflectionTexture, reflectTextureCoords).rgb;
    vec3 refractColor = texture(refractionTexture, refractTextureCoords).rgb;

    float waterDepth = calculateWaterDepth(texCoordsReal);

    refractColor = calculateMurkiness(refractColor, waterDepth);
    reflectColor = mix(reflectColor, waterColor, minBlueness);

    vec3 finalColor = mix(reflectColor, refractColor, calculateFresnel(vToCameraVector, vNormal));
    finalColor = finalColor * vDiffuse + vSpecular;

    for (int i = 0; i < LIGHT_COUNT; i++) {
        if (shadows[i].enabled > 0.5) {
            float mapSize = 2048.0;
            float texelSize = 1.0 / mapSize;
            float total = 0.0;

            for (int x = -PCF_COUNT; x <= PCF_COUNT; x++) {
                for (int y = -PCF_COUNT; y <= PCF_COUNT; y++) {
                    float objectNearestToLight = texture(shadowMaps[i], vShadowCoords[i].xy + vec2(x, y) * texelSize).r;
                    if (vShadowCoords[i].z > objectNearestToLight) {
                        total += 1.0;
                    }
                }
            }
            total /= TOTAL_TEXELS;
            float lightFactor = max(1.0 - (total * vShadowCoords[i].w), 0.4);
            finalColor *= lightFactor;
        }
    }

    float pointShadowFactor = pointShadowCalculation(vPosition.xyz);
    finalColor *= (1 - pointShadowFactor);

    fColor = vec4(finalColor, clamp(waterDepth / edgeSoftness, 0.0, 1.0));
    fColor = mix(fogColor, fColor, vVisibility);

}