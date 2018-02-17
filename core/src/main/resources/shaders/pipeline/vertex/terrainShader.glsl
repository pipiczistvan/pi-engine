#version 330 core

const int POINT_LIGHT_COUNT = ${lighting.point.light.count};
const int DIRECTIONAL_LIGHT_COUNT = ${lighting.directional.light.count};
const float DIRECTIONAL_SHADOW_DISTANCE = ${lighting.directional.shadow.distance};
const float DIRECTIONAL_SHADOW_TRANSITION_DISTANCE = ${lighting.directional.shadow.transition.distance};

#import "struct/fog";
#import "struct/directionalLight";
#import "struct/pointLight";
#import "struct/directionalShadow";

#import "function/light/diffuse";
#import "function/light/attenuation";
#import "function/fog";

layout (location = 0) in vec3 Position;
layout (location = 2) in vec3 Color;
layout (location = 3) in vec3 Normal;

flat out vec3 vColor;
flat out vec3 vNormal;
flat out vec3 vdDiffuse[DIRECTIONAL_LIGHT_COUNT];
flat out vec3 vpDiffuse[POINT_LIGHT_COUNT];
flat out float vAttenuation[POINT_LIGHT_COUNT];
out vec3 vPosition;
out float vFogFactor;
out vec4 vShadowCoords[DIRECTIONAL_LIGHT_COUNT];

uniform DirectionalLight directionalLights[DIRECTIONAL_LIGHT_COUNT];
uniform PointLight pointLights[POINT_LIGHT_COUNT];
uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;
uniform vec4 clippingPlane;
uniform DirectionalShadow directionalShadows[DIRECTIONAL_LIGHT_COUNT];
uniform Fog fog;

void main(void) {
    vec4 worldPosition = vec4(Position, 1.0);
    vec4 viewPosition = viewMatrix * worldPosition;
    vec4 worldNormal = vec4(Normal, 0.0);
    gl_ClipDistance[0] = dot(worldPosition, clippingPlane);

    float distance = length(viewPosition);

    vColor = Color;
    vNormal = normalize(worldNormal.xyz);
    vPosition = worldPosition.xyz;
    vFogFactor = fog.enabled > 0.5 ? calculateFogFactor(viewPosition.xyz, fog.density, fog.gradient) : 1.0;

    float directionalShadowDistance = distance - (DIRECTIONAL_SHADOW_DISTANCE - DIRECTIONAL_SHADOW_TRANSITION_DISTANCE);
    directionalShadowDistance /= DIRECTIONAL_SHADOW_TRANSITION_DISTANCE;
    for (int i = 0; i < DIRECTIONAL_LIGHT_COUNT; i++) {
        vShadowCoords[i] = directionalShadows[i].spaceMatrix * worldPosition;
        vShadowCoords[i].w = clamp(1.0 - directionalShadowDistance, 0.0, 1.0);
    }

    // LIGHTING
    for(int i = 0; i < DIRECTIONAL_LIGHT_COUNT; i++) {
        if (directionalLights[i].enabled > 0.5) {
            vdDiffuse[i] = calculateDiffuseLightFactor(directionalLights[i].position, directionalLights[i].color.rgb, vPosition.xyz, vNormal);
        }
    }
    for (int i = 0; i < POINT_LIGHT_COUNT; i++) {
        if (pointLights[i].enabled > 0.5) {
            vAttenuation[i] = calculateLightAttenuationFactor(pointLights[i].attenuation, pointLights[i].position, vPosition.xyz);
            vpDiffuse[i] = calculateDiffuseLightFactor(pointLights[i].position, pointLights[i].color.rgb, vPosition.xyz, vNormal);
        }
    }

    gl_Position = projectionMatrix * viewPosition;
}
