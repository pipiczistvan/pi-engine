#version 330 core

const int MAX_JOINTS = 50;
const int MAX_WEIGHTS = 3;

layout (location = 0) in vec3 Position;
layout (location = 1) in vec2 TextureCoord;
layout (location = 3) in vec3 Normal;
layout (location = 5) in ivec3 JointIndex;
layout (location = 6) in vec3 Weight;

out vec2 vTextureCoord;
out vec3 vNormal;

uniform mat4 jointTransforms[MAX_JOINTS];
uniform mat4 projectionViewMatrix;

void main(void) {
	vec4 totalLocalPos = vec4(0.0);
	vec4 totalNormal = vec4(0.0);

	for(int i = 0; i < MAX_WEIGHTS; i++) {
		mat4 jointTransform = jointTransforms[JointIndex[i]];
		vec4 posePosition = jointTransform * vec4(Position, 1.0);
		totalLocalPos += posePosition * Weight[i];

		vec4 worldNormal = jointTransform * vec4(Normal, 0.0);
		totalNormal += worldNormal * Weight[i];
	}

	gl_Position = projectionViewMatrix * totalLocalPos;
	vNormal = totalNormal.xyz;
	vTextureCoord = TextureCoord;
}
