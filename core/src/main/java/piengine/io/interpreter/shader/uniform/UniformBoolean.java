package piengine.io.interpreter.shader.uniform;

import piengine.io.interpreter.shader.Shader;

import static org.lwjgl.opengl.GL20.glUniform1f;

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
        glUniform1f(location, value ? 1 : 0);
    }

    @Override
    protected Boolean copyValue(final Boolean value) {
        return value;
    }
}
