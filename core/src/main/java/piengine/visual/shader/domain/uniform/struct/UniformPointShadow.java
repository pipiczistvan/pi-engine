package piengine.visual.shader.domain.uniform.struct;

import piengine.visual.lighting.point.shadow.domain.PointShadow;
import piengine.visual.shader.domain.Shader;
import piengine.visual.shader.domain.uniform.UniformBoolean;
import piengine.visual.shader.domain.uniform.UniformVector3f;

import static piengine.visual.shader.domain.uniform.UniformBoolean.uniformBoolean;
import static piengine.visual.shader.domain.uniform.UniformVector3f.uniformVector3f;

public class UniformPointShadow extends UniformStruct<PointShadow> {

    private final UniformBoolean enabled;
    private final UniformVector3f position;

    private UniformPointShadow(final Shader shader, final String variable) {
        this.enabled = uniformBoolean(shader, variable + ".enabled");
        this.position = uniformVector3f(shader, variable + ".position");
    }

    public static UniformPointShadow uniformPointShadow(final Shader shader, final String variable) {
        return new UniformPointShadow(shader, variable);
    }

    public static UniformPointShadow[] uniformPointShadow(final Shader shader, final String variable, final int count) {
        UniformPointShadow[] uniforms = new UniformPointShadow[count];
        for (int i = 0; i < count; i++) {
            uniforms[i] = uniformPointShadow(shader, variable + "[" + i + "]");
        }
        return uniforms;
    }

    @Override
    public void load(final PointShadow value) {
        if (value != null) {
            enabled.load(true);
            position.load(value.getPosition());
        } else {
            enabled.load(false);
        }
    }
}
