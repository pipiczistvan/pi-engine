#version 330 core

const int MAX_JOINTS = ${animation.skeleton.max.joints};
const int MAX_WEIGHTS = ${animation.skeleton.max.weights};

layout (location = 0) in vec3 Position;
layout (location = 5) in ivec3 JointIndex;
layout (location = 6) in vec3 Weight;

uniform int renderStage;
uniform mat4 modelMatrix;
uniform mat4 jointTransforms[MAX_JOINTS];

void main(void) {
    if (renderStage == 0) {
        gl_Position = modelMatrix * vec4(Position, 1.0);
    } else if (renderStage == 1) {
        vec4 totalLocalPos = vec4(0.0);
        for(int i = 0; i < MAX_WEIGHTS; i++) {
            mat4 jointTransform = jointTransforms[JointIndex[i]];
            vec4 posePosition = jointTransform * vec4(Position, 1.0);
            totalLocalPos += posePosition * Weight[i];
        }

        gl_Position = modelMatrix * totalLocalPos;
    }
}
