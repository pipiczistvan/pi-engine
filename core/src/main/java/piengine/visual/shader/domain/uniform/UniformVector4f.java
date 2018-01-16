package piengine.visual.shader.domain.uniform;

import org.joml.Vector4f;
import piengine.visual.shader.domain.Shader;

public class UniformVector4f extends Uniform<Vector4f> {

    public UniformVector4f(final Shader shader, final String variable) {
        super(shader, variable);
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
