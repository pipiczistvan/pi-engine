#version 330 core

const int DIRECTIONAL_LIGHT_COUNT = ${lighting.directional.light.count};
const int POINT_LIGHT_COUNT = ${lighting.point.light.count};

#import "struct/fog";
#import "struct/directionalLight";
#import "struct/pointLight";

#import "function/light/diffuse";
#import "function/light/attenuation";
#import "function/fog";

layout (location = 0) in vec3 Position;
layout (location = 1) in vec2 TextureCoord;
layout (location = 3) in vec3 Normal;

out vec2 vTextureCoord;
flat out vec4 vColor;
out float vFogFactor;

uniform mat4 modelMatrix;
uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;
uniform DirectionalLight directionalLights[DIRECTIONAL_LIGHT_COUNT];
uniform PointLight pointLights[POINT_LIGHT_COUNT];
uniform Fog fog;
uniform vec4 clippingPlane;
uniform float lightEmitter;

void main(void) {
    vec4 worldPosition = modelMatrix * vec4(Position, 1.0);
    vec4 viewPosition = viewMatrix * worldPosition;
    vec4 worldNormal = modelMatrix * vec4(Normal, 0.0);
    gl_ClipDistance[0] = dot(worldPosition, clippingPlane);

    vec3 normalizedVertexNormal = normalize(worldNormal.xyz);

    vec3 lightFactor = vec3(0);
    if (lightEmitter > 0.5) {
        lightFactor = vec3(1);
    } else {
        for (int i = 0; i < DIRECTIONAL_LIGHT_COUNT; i++) {
            if (directionalLights[i].enabled > 0.5) {
                lightFactor += calculateDiffuseLightFactor(directionalLights[i].position, directionalLights[i].color.rgb, worldPosition.xyz, normalizedVertexNormal);
            }
        }
        for (int i = 0; i < POINT_LIGHT_COUNT; i++) {
            if (pointLights[i].enabled > 0.5) {
                lightFactor += calculateDiffuseLightFactor(pointLights[i].position, pointLights[i].color.rgb, worldPosition.xyz, normalizedVertexNormal) /
                        calculateLightAttenuationFactor(pointLights[i].attenuation, pointLights[i].position, worldPosition.xyz);
            }
        }

        lightFactor = max(lightFactor, 0.1);
    }

    vColor = vec4(lightFactor.xyz, 1.0);
    vTextureCoord = TextureCoord;
    vFogFactor = fog.enabled > 0.5 ? calculateFogFactor(viewPosition.xyz, fog.density, fog.gradient) : 1.0;
    gl_Position = projectionMatrix * viewPosition;
}
