package piengine.visual.shader.domain.uniform.struct;

import piengine.visual.light.domain.Light;
import piengine.visual.shader.domain.Shader;
import piengine.visual.shader.domain.uniform.UniformColor;
import piengine.visual.shader.domain.uniform.UniformVector3f;

import static piengine.visual.shader.domain.uniform.UniformColor.uniformColor;
import static piengine.visual.shader.domain.uniform.UniformVector3f.uniformVector3f;

public class UniformLight extends UniformStruct<Light> {

    private final UniformColor color;
    private final UniformVector3f position;
    private final UniformVector3f attenuation;

    private UniformLight(final Shader shader, final String variable) {
        this.color = uniformColor(shader, variable + ".color");
        this.position = uniformVector3f(shader, variable + ".position");
        this.attenuation = uniformVector3f(shader, variable + ".attenuation");
    }

    public static UniformLight uniformLight(final Shader shader, final String variable) {
        return new UniformLight(shader, variable);
    }

    public static UniformLight[] uniformLight(final Shader shader, final String variable, final int count) {
        UniformLight[] uniforms = new UniformLight[count];
        for (int i = 0; i < count; i++) {
            uniforms[i] = uniformLight(shader, variable + "[" + i + "]");
        }
        return uniforms;
    }

    @Override
    public void load(final Light value) {
        color.load(value.getColor());
        position.load(value.getPosition());
        attenuation.load(value.getAttenuation());
    }
}
