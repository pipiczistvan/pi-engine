package piengine.visual.postprocessing.shader;

import piengine.visual.shader.domain.Shader;
import piengine.visual.shader.domain.ShaderDao;
import piengine.visual.shader.domain.uniform.UniformFloat;

import static piengine.visual.shader.domain.uniform.UniformFloat.uniformFloat;

public class VerticalBlurEffectShader extends Shader {

    private final UniformFloat textureHeight = uniformFloat(this, "textureHeight");

    public VerticalBlurEffectShader(final ShaderDao dao) {
        super(dao);
    }

    public VerticalBlurEffectShader loadTextureHeight(final float value) {
        textureHeight.load(value);
        return this;
    }
}
