package piengine.io.interpreter.shader.uniform.struct;

import piengine.io.interpreter.shader.Shader;
import piengine.io.interpreter.shader.uniform.UniformBoolean;
import piengine.io.interpreter.shader.uniform.UniformVector3f;
import piengine.visual.lighting.point.shadow.domain.PointShadow;

import static piengine.io.interpreter.shader.uniform.UniformVector3f.uniformVector3f;

public class UniformPointShadow extends UniformStruct<PointShadow> {

    private final UniformBoolean enabled;
    private final UniformVector3f position;

    private UniformPointShadow(final Shader shader, final String variable) {
        this.enabled = UniformBoolean.uniformBoolean(shader, variable + ".enabled");
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
