package piengine.visual.shader.domain.uniform;

import piengine.visual.shader.domain.Shader;

public class UniformBoolean extends Uniform<Boolean> {

    public UniformBoolean(final Shader shader, final String variable) {
        super(shader, variable);
    }

    @Override
    public void load(final Boolean value) {
        shaderService.loadUniform(location, value);
    }
}
