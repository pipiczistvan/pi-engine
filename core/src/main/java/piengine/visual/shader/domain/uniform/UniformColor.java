package piengine.visual.shader.domain.uniform;

import piengine.core.base.type.color.Color;
import piengine.visual.shader.domain.Shader;

public class UniformColor extends Uniform<Color> {

    public UniformColor(final Shader shader, final String variable) {
        super(shader, variable);
    }

    @Override
    protected void loadToShader(final Color value) {
        shaderService.loadUniform(location, value);
    }

    @Override
    protected Color copyValue(final Color value) {
        return new Color(value);
    }
}
