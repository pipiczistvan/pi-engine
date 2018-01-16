package piengine.visual.shader.domain.uniform;

import piengine.visual.shader.domain.Shader;

public class UniformInteger extends Uniform<Integer> {

    public UniformInteger(final Shader shader, final String variable) {
        super(shader, variable);
    }

    @Override
    protected void loadToShader(final Integer value) {
        shaderService.loadUniform(location, value);
    }

    @Override
    protected Integer copyValue(final Integer value) {
        return value;
    }
}
