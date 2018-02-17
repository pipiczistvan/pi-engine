float calculateDirectionalShadowFactor(vec4 shadowCoords, sampler2D shadowMap, int mapSize) {
    float texelSize = 1.0 / mapSize;
    float total = 0.0;

    for (int x = -DIRECTIONAL_SHADOW_PCF_COUNT; x <= DIRECTIONAL_SHADOW_PCF_COUNT; x++) {
        for (int y = -DIRECTIONAL_SHADOW_PCF_COUNT; y <= DIRECTIONAL_SHADOW_PCF_COUNT; y++) {
            float objectNearestToLight = texture(shadowMap, shadowCoords.xy + vec2(x, y) * texelSize).r;
            if (shadowCoords.z > objectNearestToLight) {
                total += 1.0;
            }
        }
    }
    total /= TOTAL_TEXELS;

    return min(total * shadowCoords.w, SHADOW_DARKNESS);
}
