#version 330 core

const float SHADOW_DARKNESS = ${lighting.shadow.darkness};
const int DIRECTIONAL_LIGHT_COUNT = ${lighting.directional.light.count};
const int POINT_LIGHT_COUNT = ${lighting.point.light.count};
const int DIRECTIONAL_SHADOW_PCF_COUNT = ${lighting.directional.shadow.pcf.count};
const float TOTAL_TEXELS = (DIRECTIONAL_SHADOW_PCF_COUNT * 2.0 + 1.0) * (DIRECTIONAL_SHADOW_PCF_COUNT * 2.0 + 1.0);
const float POINT_SHADOW_DISTANCE = ${lighting.point.shadow.distance};
const float POINT_SHADOW_TRANSITION_DISTANCE = ${lighting.point.shadow.transition.distance};
const float POINT_SHADOW_TRANSITION_LENGTH = 1.0 - (POINT_SHADOW_DISTANCE - POINT_SHADOW_TRANSITION_DISTANCE) / POINT_SHADOW_DISTANCE;
const float WATER_FRESNEL_EFFECT = ${water.fresnel.effect};
const float WATER_EDGE_SOFTNESS = ${water.edge.softness};
const float WATER_MIN_COLOR = ${water.color.min};
const float WATER_MAX_COLOR = ${water.color.max};
const float WATER_MURKY_DEPTH = ${water.murky.depth};
const float CAMERA_NEAR_PLANE = ${camera.near.plane};
const float CAMERA_FAR_PLANE = ${camera.far.plane};
const vec3 sampleOffsetDirections[20] = vec3[]
(
   vec3( 1,  1,  1), vec3( 1, -1,  1), vec3(-1, -1,  1), vec3(-1,  1,  1),
   vec3( 1,  1, -1), vec3( 1, -1, -1), vec3(-1, -1, -1), vec3(-1,  1, -1),
   vec3( 1,  1,  0), vec3( 1, -1,  0), vec3(-1, -1,  0), vec3(-1,  1,  0),
   vec3( 1,  0,  1), vec3(-1,  0,  1), vec3( 1,  0, -1), vec3(-1,  0, -1),
   vec3( 0,  1,  1), vec3( 0, -1,  1), vec3( 0, -1, -1), vec3( 0,  1, -1)
);

#import "struct/fog";
#import "struct/directionalLight";
#import "struct/pointLight";
#import "struct/directionalShadow";
#import "struct/pointShadow";

#import "function/shadow/point";
#import "function/shadow/directional";

in vec4 vClipSpaceGrid;
in vec4 vClipSpaceReal;
in vec3 vToCameraVector;
flat in vec3 vNormal;
flat in vec3 vdDiffuse[DIRECTIONAL_LIGHT_COUNT];
flat in vec3 vdSpecular[DIRECTIONAL_LIGHT_COUNT];
flat in vec3 vpDiffuse[POINT_LIGHT_COUNT];
flat in vec3 vpSpecular[POINT_LIGHT_COUNT];
flat in float vAttenuation[POINT_LIGHT_COUNT];
in float vFogFactor;
in vec4 vShadowCoords[DIRECTIONAL_LIGHT_COUNT];
in vec4 vPosition;

out vec4 fColor;

uniform DirectionalLight directionalLights[DIRECTIONAL_LIGHT_COUNT];
uniform DirectionalShadow directionalShadows[DIRECTIONAL_LIGHT_COUNT];
uniform sampler2D directionalShadowMaps[DIRECTIONAL_LIGHT_COUNT];
uniform PointLight pointLights[POINT_LIGHT_COUNT];
uniform PointShadow pointShadows[POINT_LIGHT_COUNT];
uniform samplerCube pointShadowMaps[POINT_LIGHT_COUNT];
uniform sampler2D reflectionTexture;
uniform sampler2D refractionTexture;
uniform sampler2D depthTexture;
uniform Fog fog;
uniform vec3 cameraPosition;
uniform vec4 waterColor;

vec3 calculateMurkiness(vec3 refractColor, vec3 waterColor, float waterDepth) {
    float murkyFactor = smoothstep(0, WATER_MURKY_DEPTH, waterDepth);
    float murkiness = WATER_MIN_COLOR + murkyFactor * (WATER_MAX_COLOR - WATER_MIN_COLOR);
    return mix(refractColor, waterColor, murkiness);
}

float calculateLinearDepth(float zDepth) {
    return 2.0 * CAMERA_NEAR_PLANE * CAMERA_FAR_PLANE / (CAMERA_FAR_PLANE + CAMERA_NEAR_PLANE - (2.0 * zDepth - 1.0) * (CAMERA_FAR_PLANE - CAMERA_NEAR_PLANE));
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
    refractiveFactor = pow(refractiveFactor, WATER_FRESNEL_EFFECT);
    return clamp(refractiveFactor, 0.0, 1.0);
}

vec2 clipSpaceToTextureCoords(vec4 clipSpace) {
    vec2 normalisedDiviceSpace = (clipSpace.xy / clipSpace.w);
    vec2 texCoords = normalisedDiviceSpace / 2.0 + 0.5;
    return clamp(texCoords, 0.002, 0.998);
}

void main(void) {
    // REFLECTION & REFRACTION

    vec2 texCoordsGrid = clipSpaceToTextureCoords(vClipSpaceGrid);
    vec2 texCoordsReal = clipSpaceToTextureCoords(vClipSpaceReal);

    vec2 reflectTextureCoords = vec2(texCoordsGrid.x, 1.0 - texCoordsGrid.y);
    vec2 refractTextureCoords = texCoordsGrid;

    vec3 reflectColor = texture(reflectionTexture, reflectTextureCoords).rgb;
    vec3 refractColor = texture(refractionTexture, refractTextureCoords).rgb;

    float waterDepth = calculateWaterDepth(texCoordsReal);

    refractColor = calculateMurkiness(refractColor, waterColor.rgb, waterDepth);
    reflectColor = mix(reflectColor, waterColor.rgb, WATER_MIN_COLOR);

    vec3 textureColor = mix(reflectColor, refractColor, calculateFresnel(vToCameraVector, vNormal));

    // SHADOW

    vec3 finalDiffuse = vec3(0.0);
    vec3 finalSpecular = vec3(0.0);
    for(int i = 0; i < DIRECTIONAL_LIGHT_COUNT; i++) {
        if (directionalLights[i].enabled > 0.5) {
            float shadowFactor = 0.0;
            if (directionalShadows[i].enabled > 0.5) {
                shadowFactor = calculateDirectionalShadowFactor(vShadowCoords[i], directionalShadowMaps[i], directionalShadows[i].mapSize);
            }
            finalDiffuse += max(vdDiffuse[i] - shadowFactor, 0.0);
            finalSpecular += max(vdSpecular[i] - shadowFactor, 0.0);
        }
    }
    for (int i = 0; i < POINT_LIGHT_COUNT; i++) {
        if (pointLights[i].enabled > 0.5) {
            float shadowFactor = 0.0;
            if (pointShadows[i].enabled > 0.5) {
                shadowFactor = calculatePointShadowFactor(vPosition.xyz, cameraPosition, pointShadows[i].position, pointShadowMaps[i]);
            }
            finalDiffuse += max(vpDiffuse[i] - shadowFactor, 0.0) / vAttenuation[i];
            finalSpecular += max(vpSpecular[i] - shadowFactor, 0.0) / vAttenuation[i];
        }
    }

    // OUTPUT

    vec3 finalColor = textureColor * finalDiffuse + finalSpecular;
    fColor = vec4(finalColor, clamp(waterDepth / WATER_EDGE_SOFTNESS, 0.0, 1.0));

    // FOG
    if (fog.enabled > 0.5) {
        fColor = mix(fColor, fog.color, vFogFactor);
    }
}
