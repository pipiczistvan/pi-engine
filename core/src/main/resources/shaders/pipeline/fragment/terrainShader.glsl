#version 330 core

const float SHADOW_DARKNESS = ${lighting.shadow.darkness};
const int DIRECTIONAL_LIGHT_COUNT = ${lighting.directional.light.count};
const int POINT_LIGHT_COUNT = ${lighting.point.light.count};
const int DIRECTIONAL_SHADOW_PCF_COUNT = ${lighting.directional.shadow.pcf.count};
const float TOTAL_TEXELS = (DIRECTIONAL_SHADOW_PCF_COUNT * 2.0 + 1.0) * (DIRECTIONAL_SHADOW_PCF_COUNT * 2.0 + 1.0);
const float POINT_SHADOW_DISTANCE = ${lighting.point.shadow.distance};
const float POINT_SHADOW_TRANSITION_DISTANCE = ${lighting.point.shadow.transition.distance};
const float POINT_SHADOW_TRANSITION_LENGTH = 1.0 - (POINT_SHADOW_DISTANCE - POINT_SHADOW_TRANSITION_DISTANCE) / POINT_SHADOW_DISTANCE;
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

flat in vec3 vColor;
flat in vec3 vNormal;
flat in vec3 vdDiffuse[DIRECTIONAL_LIGHT_COUNT];
flat in vec3 vpDiffuse[POINT_LIGHT_COUNT];
flat in float vAttenuation[POINT_LIGHT_COUNT];
in vec3 vPosition;
in float vFogFactor;
in vec4 vShadowCoords[DIRECTIONAL_LIGHT_COUNT];

out vec4 fColor;

uniform DirectionalLight directionalLights[DIRECTIONAL_LIGHT_COUNT];
uniform DirectionalShadow directionalShadows[DIRECTIONAL_LIGHT_COUNT];
uniform sampler2D directionalShadowMaps[DIRECTIONAL_LIGHT_COUNT];
uniform PointLight pointLights[POINT_LIGHT_COUNT];
uniform PointShadow pointShadows[POINT_LIGHT_COUNT];
uniform samplerCube pointShadowMaps[POINT_LIGHT_COUNT];
uniform Fog fog;
uniform vec3 cameraPosition;

void main(void) {

    vec3 finalDiffuse = vec3(0.0);
    for(int i = 0; i < DIRECTIONAL_LIGHT_COUNT; i++) {
        if (directionalLights[i].enabled > 0.5) {
            float shadowFactor = 0.0;
            if (directionalShadows[i].enabled > 0.5) {
                shadowFactor = calculateDirectionalShadowFactor(vShadowCoords[i], directionalShadowMaps[i], directionalShadows[i].mapSize);
            }
            finalDiffuse += max(vdDiffuse[i] - shadowFactor, 0.0);
        }
    }
    for (int i = 0; i < POINT_LIGHT_COUNT; i++) {
        if (pointLights[i].enabled > 0.5) {
            float shadowFactor = 0.0;
            if (pointShadows[i].enabled > 0.5) {
                shadowFactor = calculatePointShadowFactor(vPosition.xyz, cameraPosition, pointShadows[i].position, pointShadowMaps[i]);
            }
            finalDiffuse += max(vpDiffuse[i] - shadowFactor, 0.0) / vAttenuation[i];
        }
    }

    fColor = vec4(finalDiffuse * vColor, 1.0);
    // FOG
    if (fog.enabled > 0.5) {
        fColor = mix(fColor, fog.color, vFogFactor);
    }
}
