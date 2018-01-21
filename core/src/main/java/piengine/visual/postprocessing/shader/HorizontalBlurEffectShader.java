package piengine.visual.postprocessing.shader;

import piengine.visual.shader.domain.Shader;
import piengine.visual.shader.domain.ShaderDao;
import piengine.visual.shader.domain.uniform.UniformFloat;

import static piengine.visual.shader.domain.uniform.UniformFloat.uniformFloat;

public class HorizontalBlurEffectShader extends Shader {

    private final UniformFloat textureWidth = uniformFloat(this, "textureWidth");

    public HorizontalBlurEffectShader(final ShaderDao dao) {
        super(dao);
    }

    public HorizontalBlurEffectShader loadTextureWidth(final float value) {
        textureWidth.load(value);
        return this;
    }
}
