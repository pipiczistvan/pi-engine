package piengine.io.interpreter.shader.uniform;

import org.joml.Vector2f;
import piengine.io.interpreter.shader.Shader;

import static org.lwjgl.opengl.GL20.glUniform2f;

public class UniformVector2f extends Uniform<Vector2f> {

    private UniformVector2f(final Shader shader, final String variable) {
        super(shader, variable);
    }

    public static UniformVector2f uniformVector2f(final Shader shader, final String variable) {
        return new UniformVector2f(shader, variable);
    }

    public static UniformVector2f[] uniformVector2f(final Shader shader, final String variable, final int count) {
        UniformVector2f[] uniforms = new UniformVector2f[count];
        for (int i = 0; i < count; i++) {
            uniforms[i] = uniformVector2f(shader, variable + "[" + i + "]");
        }
        return uniforms;
    }

    @Override
    protected void loadToShader(final Vector2f value) {
        glUniform2f(location, value.x, value.y);
    }

    @Override
    protected Vector2f copyValue(final Vector2f value) {
        return new Vector2f(value);
    }
}
