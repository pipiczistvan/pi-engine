package piengine.visual.shader.domain.uniform;

import piengine.visual.shader.domain.Shader;

public class UniformInteger extends Uniform<Integer> {

    private UniformInteger(final Shader shader, final String variable) {
        super(shader, variable);
    }

    public static UniformInteger uniformInteger(final Shader shader, final String variable) {
        return new UniformInteger(shader, variable);
    }

    public static UniformInteger[] uniformInteger(final Shader shader, final String variable, final int count) {
        UniformInteger[] uniforms = new UniformInteger[count];
        for (int i = 0; i < count; i++) {
            uniforms[i] = uniformInteger(shader, variable + "[" + i + "]");
        }
        return uniforms;
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
