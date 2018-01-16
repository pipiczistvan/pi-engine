package piengine.visual.shader.domain.uniform;

import piengine.core.base.type.color.Color;
import piengine.visual.shader.domain.Shader;

public class UniformColor extends Uniform<Color> {

    public UniformColor(final Shader shader, final String variable) {
        super(shader, variable);
    }

    @Override
    public void load(final Color value) {
        shaderService.loadUniform(location, value);
    }
}
