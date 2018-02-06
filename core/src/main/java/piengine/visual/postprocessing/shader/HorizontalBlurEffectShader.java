package piengine.visual.postprocessing.shader;

import piengine.io.interpreter.shader.Shader;
import piengine.io.interpreter.shader.uniform.UniformFloat;
import piengine.io.loader.glsl.domain.GlslDto;

import static piengine.io.interpreter.shader.uniform.UniformFloat.uniformFloat;

public class HorizontalBlurEffectShader extends Shader {

    private final UniformFloat textureWidth = uniformFloat(this, "textureWidth");

    public HorizontalBlurEffectShader(final GlslDto glsl) {
        super(glsl);
    }

    public HorizontalBlurEffectShader loadTextureWidth(final float value) {
        textureWidth.load(value);
        return this;
    }
}
