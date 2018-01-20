package piengine.visual.shader.domain.uniform.struct;

import piengine.visual.lighting.directional.shadow.domain.DirectionalShadow;
import piengine.visual.shader.domain.Shader;
import piengine.visual.shader.domain.uniform.UniformBoolean;
import piengine.visual.shader.domain.uniform.UniformInteger;
import piengine.visual.shader.domain.uniform.UniformMatrix4f;

import static piengine.visual.shader.domain.uniform.UniformBoolean.uniformBoolean;
import static piengine.visual.shader.domain.uniform.UniformInteger.uniformInteger;
import static piengine.visual.shader.domain.uniform.UniformMatrix4f.uniformMatrix4f;

public class UniformDirectionalShadow extends UniformStruct<DirectionalShadow> {

    private final UniformBoolean enabled;
    private final UniformMatrix4f spaceMatrix;
    private final UniformInteger mapSize;

    private UniformDirectionalShadow(final Shader shader, final String variable) {
        this.enabled = uniformBoolean(shader, variable + ".enabled");
        this.spaceMatrix = uniformMatrix4f(shader, variable + ".spaceMatrix");
        this.mapSize = uniformInteger(shader, variable + ".mapSize");
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
            mapSize.load(value.getShadowMap().resolution.x);
        } else {
            enabled.load(false);
        }
    }
}
