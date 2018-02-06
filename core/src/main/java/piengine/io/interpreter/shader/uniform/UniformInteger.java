package piengine.io.interpreter.shader.uniform;

import piengine.io.interpreter.shader.Shader;

import static org.lwjgl.opengl.GL20.glUniform1i;

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
        glUniform1i(location, value);
    }

    @Override
    protected Integer copyValue(final Integer value) {
        return value;
    }
}
