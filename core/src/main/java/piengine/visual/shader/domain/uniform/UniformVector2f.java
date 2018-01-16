package piengine.visual.shader.domain.uniform;

import org.joml.Vector2f;
import piengine.visual.shader.domain.Shader;

public class UniformVector2f extends Uniform<Vector2f> {

    public UniformVector2f(final Shader shader, final String variable) {
        super(shader, variable);
    }

    @Override
    public void load(final Vector2f value) {
        shaderService.loadUniform(location, value);
    }
}
