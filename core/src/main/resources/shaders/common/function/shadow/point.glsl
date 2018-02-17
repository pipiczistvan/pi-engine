float calculatePointShadowFactor(vec3 fragPos, vec3 viewPosition, vec3 lightPosition, samplerCube shadowCubeMap) {
    vec3 fragToLight = fragPos - lightPosition;
    float currentDepth = length(fragToLight);
    currentDepth /= POINT_SHADOW_DISTANCE;
    float viewDistance = length(viewPosition - fragPos);

    float shadow  = 0.0;
    float samples = 20;
    float diskRadius = (1.0 + (viewDistance / POINT_SHADOW_DISTANCE)) / 25.0;
    for(int i = 0; i < samples; i++) {
        float closestDepth = texture(shadowCubeMap, fragToLight + sampleOffsetDirections[i] * diskRadius).r;
        if(currentDepth > closestDepth) {
            shadow += 1.0;
        }
    }

    float strength = max(1.0 - currentDepth, 0.0) / POINT_SHADOW_TRANSITION_LENGTH;

    return min(shadow / samples * strength, SHADOW_DARKNESS);
}
