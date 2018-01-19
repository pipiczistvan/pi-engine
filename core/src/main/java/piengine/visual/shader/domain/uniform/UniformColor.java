package piengine.visual.shader.domain.uniform;

import piengine.core.base.type.color.Color;
import piengine.visual.shader.domain.Shader;

public class UniformColor extends Uniform<Color> {

    private UniformColor(final Shader shader, final String variable) {
        super(shader, variable);
    }

    public static UniformColor uniformColor(final Shader shader, final String variable) {
        return new UniformColor(shader, variable);
    }

    public static UniformColor[] uniformColor(final Shader shader, final String variable, final int count) {
        UniformColor[] uniforms = new UniformColor[count];
        for (int i = 0; i < count; i++) {
            uniforms[i] = uniformColor(shader, variable + "[" + i + "]");
        }
        return uniforms;
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
