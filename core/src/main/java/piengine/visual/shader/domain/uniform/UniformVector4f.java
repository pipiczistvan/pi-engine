package piengine.visual.shader.domain.uniform;

import org.joml.Vector4f;
import piengine.visual.shader.domain.Shader;

public class UniformVector4f extends Uniform<Vector4f> {

    private UniformVector4f(final Shader shader, final String variable) {
        super(shader, variable);
    }

    public static UniformVector4f uniformVector4f(final Shader shader, final String variable) {
        return new UniformVector4f(shader, variable);
    }

    public static UniformVector4f[] uniformVector4f(final Shader shader, final String variable, final int count) {
        UniformVector4f[] uniforms = new UniformVector4f[count];
        for (int i = 0; i < count; i++) {
            uniforms[i] = uniformVector4f(shader, variable + "[" + i + "]");
        }
        return uniforms;
    }

    @Override
    protected void loadToShader(final Vector4f value) {
        shaderService.loadUniform(location, value);
    }

    @Override
    protected Vector4f copyValue(final Vector4f value) {
        return new Vector4f(value);
    }
}
