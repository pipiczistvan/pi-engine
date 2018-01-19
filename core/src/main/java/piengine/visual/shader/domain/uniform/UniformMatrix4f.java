package piengine.visual.shader.domain.uniform;

import org.joml.Matrix4f;
import piengine.visual.shader.domain.Shader;

public class UniformMatrix4f extends Uniform<Matrix4f> {

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
        shaderService.loadUniform(location, value);
    }

    @Override
    protected Matrix4f copyValue(final Matrix4f value) {
        return new Matrix4f(value);
    }
}
