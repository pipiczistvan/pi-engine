float calculateLightAttenuationFactor(vec3 attenuation, vec3 lightPosition, vec3 vertexPosition) {
    vec3 toLightVector = lightPosition - vertexPosition;
    float distance = length(toLightVector);
    return attenuation.x + (attenuation.y * distance) + (attenuation.z * distance * distance);
}
