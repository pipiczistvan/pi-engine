package piengine.visual.shader.domain.uniform;

import piengine.visual.shader.domain.Shader;

public class UniformFloat extends Uniform<Float> {

    public UniformFloat(final Shader shader, final String variable) {
        super(shader, variable);
    }

    @Override
    public void load(final Float value) {
        shaderService.loadUniform(location, value);
    }
}
