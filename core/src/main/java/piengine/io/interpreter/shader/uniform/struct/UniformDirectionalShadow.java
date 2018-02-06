package piengine.io.interpreter.shader.uniform.struct;

import piengine.io.interpreter.shader.Shader;
import piengine.io.interpreter.shader.uniform.UniformBoolean;
import piengine.io.interpreter.shader.uniform.UniformInteger;
import piengine.io.interpreter.shader.uniform.UniformMatrix4f;
import piengine.visual.lighting.directional.shadow.domain.DirectionalShadow;

import static piengine.io.interpreter.shader.uniform.UniformMatrix4f.uniformMatrix4f;

public class UniformDirectionalShadow extends UniformStruct<DirectionalShadow> {

    private final UniformBoolean enabled;
    private final UniformMatrix4f spaceMatrix;
    private final UniformInteger mapSize;

    private UniformDirectionalShadow(final Shader shader, final String variable) {
        this.enabled = UniformBoolean.uniformBoolean(shader, variable + ".enabled");
        this.spaceMatrix = uniformMatrix4f(shader, variable + ".spaceMatrix");
        this.mapSize = UniformInteger.uniformInteger(shader, variable + ".mapSize");
    }

    public static UniformDirectionalShadow uniformShadow(final Shader shader, final String variable) {
        return new UniformDirectionalShadow(shader, variable);
    }

    public static UniformDirectionalShadow[] uniformShadow(final Shader shader, final String variable, final int count) {
        UniformDirectionalShadow[] uniforms = new UniformDirectionalShadow[count];
        for (int i = 0; i < count; i++) {
            uniforms[i] = uniformShadow(shader, variable + "[" + i + "]");
        }
        return uniforms;
    }

    @Override
    public void load(final DirectionalShadow value) {
        if (value != null) {
            enabled.load(true);
            spaceMatrix.load(value.getSpaceMatrix());
            mapSize.load(value.getShadowMap().width);
        } else {
            enabled.load(false);
        }
    }
}
