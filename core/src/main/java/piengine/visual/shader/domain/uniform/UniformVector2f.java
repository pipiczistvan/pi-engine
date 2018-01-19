package piengine.visual.shader.domain.uniform;

import org.joml.Vector2f;
import piengine.visual.shader.domain.Shader;

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
        shaderService.loadUniform(location, value);
    }

    @Override
    protected Vector2f copyValue(final Vector2f value) {
        return new Vector2f(value);
    }
}
