package piengine.visual.postprocessing.shader;

import piengine.io.interpreter.shader.Shader;
import piengine.io.interpreter.shader.uniform.UniformFloat;
import piengine.io.loader.glsl.domain.GlslDto;

import static piengine.io.interpreter.shader.uniform.UniformFloat.uniformFloat;

public class VerticalBlurEffectShader extends Shader {

    private final UniformFloat textureHeight = uniformFloat(this, "textureHeight");

    public VerticalBlurEffectShader(final GlslDto glsl) {
        super(glsl);
    }

    public VerticalBlurEffectShader loadTextureHeight(final float value) {
        textureHeight.load(value);
        return this;
    }
}
