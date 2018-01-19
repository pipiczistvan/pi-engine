package piengine.visual.shader.domain.uniform;

import org.joml.Vector3f;
import piengine.visual.shader.domain.Shader;

public class UniformVector3f extends Uniform<Vector3f> {

    private UniformVector3f(final Shader shader, final String variable) {
        super(shader, variable);
    }

    public static UniformVector3f uniformVector3f(final Shader shader, final String variable) {
        return new UniformVector3f(shader, variable);
    }

    public static UniformVector3f[] uniformVector3f(final Shader shader, final String variable, final int count) {
        UniformVector3f[] uniforms = new UniformVector3f[count];
        for (int i = 0; i < count; i++) {
            uniforms[i] = uniformVector3f(shader, variable + "[" + i + "]");
        }
        return uniforms;
    }

    @Override
    protected void loadToShader(final Vector3f value) {
        shaderService.loadUniform(location, value);
    }

    @Override
    protected Vector3f copyValue(final Vector3f value) {
        return new Vector3f(value);
    }
}
