#version 330 core

const float KERNEL_VALUES[] = float[](
    0.0093,
    0.028002,
    0.065984,
    0.121703,
    0.175713,
    0.198596,
    0.175713,
    0.121703,
    0.065984,
    0.028002,
    0.0093
);
const int KERNEL_SIZE = 11;

in vec2 vBlurTextureCoords[KERNEL_SIZE];

out vec4 fColor;

uniform sampler2D originalTexture;

void main(void){
	fColor = vec4(0.0);
    for (int i = 0; i < KERNEL_SIZE; i++) {
        fColor += texture(originalTexture, vBlurTextureCoords[i]) * KERNEL_VALUES[i];
    }
}
