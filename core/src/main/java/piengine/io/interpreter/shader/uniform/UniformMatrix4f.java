package piengine.io.interpreter.shader.uniform;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import piengine.io.interpreter.shader.Shader;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;

public class UniformMatrix4f extends Uniform<Matrix4f> {

    private static FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);

    private UniformMatrix4f(final Shader shader, final String variable) {
        super(shader, variable);
    }

    public static UniformMatrix4f uniformMatrix4f(final Shader shader, final String variable) {
        return new UniformMatrix4f(shader, variable);
    }

    public static UniformMatrix4f[] uniformMatrix4f(final Shader shader, final String variable, final int count) {
        UniformMatrix4f[] uniforms = new UniformMatrix4f[count];
        for (int i = 0; i < count; i++) {
            uniforms[i] = uniformMatrix4f(shader, variable + "[" + i + "]");
        }
        return uniforms;
    }

    @Override
    protected void loadToShader(final Matrix4f value) {
        value.get(matrixBuffer);
        glUniformMatrix4fv(location, false, matrixBuffer);
    }

    @Override
    protected Matrix4f copyValue(final Matrix4f value) {
        return new Matrix4f(value);
    }
}
