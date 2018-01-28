float calculateFogFactor(vec3 viewPosition, float density, float gradient) {
    float distance = length(viewPosition);
    float visiblity = exp(-pow(distance * density, gradient));
    return 1.0 - clamp(visiblity, 0.0, 1.0);
}
