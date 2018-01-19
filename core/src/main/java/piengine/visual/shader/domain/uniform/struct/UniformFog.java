package piengine.visual.shader.domain.uniform.struct;

import piengine.visual.fog.Fog;
import piengine.visual.shader.domain.Shader;
import piengine.visual.shader.domain.uniform.UniformColor;
import piengine.visual.shader.domain.uniform.UniformFloat;

import static piengine.visual.shader.domain.uniform.UniformColor.uniformColor;
import static piengine.visual.shader.domain.uniform.UniformFloat.uniformFloat;

public class UniformFog extends UniformStruct<Fog> {

    private final UniformColor color;
    private final UniformFloat density;
    private final UniformFloat gradient;

    private UniformFog(final Shader shader, final String variable) {
        this.color = uniformColor(shader, variable + ".color");
        this.density = uniformFloat(shader, variable + ".density");
        this.gradient = uniformFloat(shader, variable + ".gradient");
    }

    public static UniformFog uniformFog(final Shader shader, final String variable) {
        return new UniformFog(shader, variable);
    }

    public static UniformFog[] uniformFog(final Shader shader, final String variable, final int count) {
        UniformFog[] uniforms = new UniformFog[count];
        for (int i = 0; i < count; i++) {
            uniforms[i] = uniformFog(shader, variable + "[" + i + "]");
        }
        return uniforms;
    }

    @Override
    public void load(final Fog value) {
        color.load(value.color);
        density.load(value.density);
        gradient.load(value.gradient);
    }
}
