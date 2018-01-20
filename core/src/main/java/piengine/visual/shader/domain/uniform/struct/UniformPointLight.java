package piengine.visual.shader.domain.uniform.struct;

import piengine.visual.lighting.point.light.domain.PointLight;
import piengine.visual.shader.domain.Shader;
import piengine.visual.shader.domain.uniform.UniformBoolean;
import piengine.visual.shader.domain.uniform.UniformColor;
import piengine.visual.shader.domain.uniform.UniformVector3f;

import static piengine.visual.shader.domain.uniform.UniformBoolean.uniformBoolean;
import static piengine.visual.shader.domain.uniform.UniformColor.uniformColor;
import static piengine.visual.shader.domain.uniform.UniformVector3f.uniformVector3f;

public class UniformPointLight extends UniformStruct<PointLight> {

    private final UniformBoolean enabled;
    private final UniformColor color;
    private final UniformVector3f position;
    private final UniformVector3f attenuation;

    private UniformPointLight(final Shader shader, final String variable) {
        this.enabled = uniformBoolean(shader, variable + ".enabled");
        this.color = uniformColor(shader, variable + ".color");
        this.position = uniformVector3f(shader, variable + ".position");
        this.attenuation = uniformVector3f(shader, variable + ".attenuation");
    }

    public static UniformPointLight uniformPointLight(final Shader shader, final String variable) {
        return new UniformPointLight(shader, variable);
    }

    public static UniformPointLight[] uniformPointLight(final Shader shader, final String variable, final int count) {
        UniformPointLight[] uniforms = new UniformPointLight[count];
        for (int i = 0; i < count; i++) {
            uniforms[i] = uniformPointLight(shader, variable + "[" + i + "]");
        }
        return uniforms;
    }

    @Override
    public void load(final PointLight value) {
        if (value != null) {
            enabled.load(true);
            color.load(value.getColor());
            position.load(value.getPosition());
            attenuation.load(value.getAttenuation());
        } else {
            enabled.load(false);
        }
    }
}
