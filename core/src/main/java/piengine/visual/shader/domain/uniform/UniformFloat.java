package piengine.visual.shader.domain.uniform;

import piengine.visual.shader.domain.Shader;

public class UniformFloat extends Uniform<Float> {

    public UniformFloat(final Shader shader, final String variable) {
        super(shader, variable);
    }

    @Override
    protected void loadToShader(final Float value) {
        shaderService.loadUniform(location, value);
    }

    @Override
    protected Float copyValue(final Float value) {
        return value;
    }
}
