package piengine.visual.shader.domain.uniform;

import piengine.visual.shader.domain.Shader;

public class UniformBoolean extends Uniform<Boolean> {

    public UniformBoolean(final Shader shader, final String variable) {
        super(shader, variable);
    }

    @Override
    protected void loadToShader(final Boolean value) {
        shaderService.loadUniform(location, value);
    }

    @Override
    protected Boolean copyValue(final Boolean value) {
        return value;
    }
}
