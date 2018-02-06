package piengine.io.interpreter.shader.uniform;

import piengine.io.interpreter.shader.Shader;

import static org.lwjgl.opengl.GL20.glUniform1f;

public class UniformFloat extends Uniform<Float> {

    private UniformFloat(final Shader shader, final String variable) {
        super(shader, variable);
    }

    public static UniformFloat uniformFloat(final Shader shader, final String variable) {
        return new UniformFloat(shader, variable);
    }

    public static UniformFloat[] uniformFloat(final Shader shader, final String variable, final int count) {
        UniformFloat[] uniforms = new UniformFloat[count];
        for (int i = 0; i < count; i++) {
            uniforms[i] = uniformFloat(shader, variable + "[" + i + "]");
        }
        return uniforms;
    }

    @Override
    protected void loadToShader(final Float value) {
        glUniform1f(location, value);
    }

    @Override
    protected Float copyValue(final Float value) {
        return value;
    }
}
