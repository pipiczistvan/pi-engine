vec3 calculateSpecularLightFactor(vec3 lightPosition, vec3 lightColor, vec3 vertexPosition, vec3 vertexNormal, vec3 toCamVector) {
    vec3 toLightVector = lightPosition - vertexPosition;
    vec3 normal = normalize(toLightVector);
	vec3 reflectedLightDirection = reflect(-normal, vertexNormal);
	float specularFactor = dot(reflectedLightDirection, toCamVector);

	specularFactor = max(specularFactor, 0.0);
	specularFactor = pow(specularFactor, shineDamper);
	return specularFactor * specularReflectivity * lightColor;
}
