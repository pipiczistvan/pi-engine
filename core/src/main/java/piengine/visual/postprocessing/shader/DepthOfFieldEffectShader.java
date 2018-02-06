package piengine.visual.postprocessing.shader;

import piengine.io.interpreter.shader.Shader;
import piengine.io.interpreter.shader.uniform.UniformInteger;
import piengine.io.loader.glsl.domain.GlslDto;

import static piengine.io.interpreter.shader.uniform.UniformInteger.uniformInteger;

public class DepthOfFieldEffectShader extends Shader {

    private final UniformInteger normalTexture = uniformInteger(this, "normalTexture");
    private final UniformInteger blurTexture = uniformInteger(this, "blurTexture");
    private final UniformInteger depthTexture = uniformInteger(this, "depthTexture");

    public DepthOfFieldEffectShader(final GlslDto glsl) {
        super(glsl);
    }

    public DepthOfFieldEffectShader loadTextureUnits() {
        normalTexture.load(0);
        blurTexture.load(1);
        depthTexture.load(2);
        return this;
    }
}
