package piengine.io.interpreter.shader.uniform.struct;

import piengine.io.interpreter.shader.Shader;
import piengine.io.interpreter.shader.uniform.UniformBoolean;
import piengine.io.interpreter.shader.uniform.UniformColor;
import piengine.io.interpreter.shader.uniform.UniformVector3f;
import piengine.visual.lighting.directional.light.domain.DirectionalLight;

import static piengine.io.interpreter.shader.uniform.UniformVector3f.uniformVector3f;

public class UniformDirectionalLight extends UniformStruct<DirectionalLight> {

    private final UniformBoolean enabled;
    private final UniformColor color;
    private final UniformVector3f position;

    private UniformDirectionalLight(final Shader shader, final String variable) {
        this.enabled = UniformBoolean.uniformBoolean(shader, variable + ".enabled");
        this.color = UniformColor.uniformColor(shader, variable + ".color");
        this.position = uniformVector3f(shader, variable + ".position");
    }

    public static UniformDirectionalLight uniformDirectionalLight(final Shader shader, final String variable) {
        return new UniformDirectionalLight(shader, variable);
    }

    public static UniformDirectionalLight[] uniformDirectionalLight(final Shader shader, final String variable, final int count) {
        UniformDirectionalLight[] uniforms = new UniformDirectionalLight[count];
        for (int i = 0; i < count; i++) {
            uniforms[i] = uniformDirectionalLight(shader, variable + "[" + i + "]");
        }
        return uniforms;
    }

    @Override
    public void load(final DirectionalLight value) {
        if (value != null) {
            enabled.load(true);
            color.load(value.getColor());
            position.load(value.getPosition());
        } else {
            enabled.load(false);
        }
    }
}
