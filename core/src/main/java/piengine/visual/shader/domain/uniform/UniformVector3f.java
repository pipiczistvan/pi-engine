package piengine.visual.shader.domain.uniform;

import org.joml.Vector3f;
import piengine.visual.shader.domain.Shader;

public class UniformVector3f extends Uniform<Vector3f> {

    public UniformVector3f(final Shader shader, final String variable) {
        super(shader, variable);
    }

    @Override
    public void load(final Vector3f value) {
        shaderService.loadUniform(location, value);
    }
}
