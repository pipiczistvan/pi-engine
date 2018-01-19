package piengine.visual.shader.domain.uniform;

import piengine.visual.shader.domain.Shader;

public class UniformBoolean extends Uniform<Boolean> {

    private UniformBoolean(final Shader shader, final String variable) {
        super(shader, variable);
    }

    public static UniformBoolean uniformBoolean(final Shader shader, final String variable) {
        return new UniformBoolean(shader, variable);
    }

    public static UniformBoolean[] uniformBoolean(final Shader shader, final String variable, final int count) {
        UniformBoolean[] uniforms = new UniformBoolean[count];
        for (int i = 0; i < count; i++) {
            uniforms[i] = uniformBoolean(shader, variable + "[" + i + "]");
        }
        return uniforms;
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
