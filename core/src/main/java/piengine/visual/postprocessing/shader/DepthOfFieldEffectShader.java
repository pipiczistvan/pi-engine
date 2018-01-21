package piengine.visual.postprocessing.shader;

import piengine.visual.shader.domain.Shader;
import piengine.visual.shader.domain.ShaderDao;
import piengine.visual.shader.domain.uniform.UniformInteger;

import static piengine.visual.shader.domain.uniform.UniformInteger.uniformInteger;

public class DepthOfFieldEffectShader extends Shader {

    private final UniformInteger normalTexture = uniformInteger(this, "normalTexture");
    private final UniformInteger blurTexture = uniformInteger(this, "blurTexture");
    private final UniformInteger depthTexture = uniformInteger(this, "depthTexture");

    public DepthOfFieldEffectShader(final ShaderDao dao) {
        super(dao);
    }

    public DepthOfFieldEffectShader loadTextureUnits() {
        normalTexture.load(0);
        blurTexture.load(1);
        depthTexture.load(2);
        return this;
    }
}
