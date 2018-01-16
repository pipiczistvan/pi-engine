package piengine.visual.shader.domain.uniform;

import org.joml.Matrix4f;
import piengine.visual.shader.domain.Shader;

public class UniformMatrix4f extends Uniform<Matrix4f> {

    public UniformMatrix4f(final Shader shader, final String variable) {
        super(shader, variable);
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
