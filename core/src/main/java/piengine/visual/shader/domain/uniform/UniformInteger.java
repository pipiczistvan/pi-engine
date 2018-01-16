package piengine.visual.shader.domain.uniform;

import piengine.visual.shader.domain.Shader;

public class UniformInteger extends Uniform<Integer> {

    public UniformInteger(final Shader shader, final String variable) {
        super(shader, variable);
    }

    @Override
    public void load(final Integer value) {
        shaderService.loadUniform(location, value);
    }
}
