package piengine.visual.shader.domain.uniform.struct;

import piengine.visual.shader.domain.Shader;
import piengine.visual.shader.domain.uniform.UniformBoolean;
import piengine.visual.shader.domain.uniform.UniformInteger;
import piengine.visual.shader.domain.uniform.UniformMatrix4f;
import piengine.visual.shadow.domain.Shadow;

import static piengine.visual.shader.domain.uniform.UniformBoolean.uniformBoolean;
import static piengine.visual.shader.domain.uniform.UniformInteger.uniformInteger;
import static piengine.visual.shader.domain.uniform.UniformMatrix4f.uniformMatrix4f;

public class UniformShadow extends UniformStruct<Shadow> {

    private final UniformBoolean enabled;
    private final UniformMatrix4f spaceMatrix;
    private final UniformInteger mapSize;

    private UniformShadow(final Shader shader, final String variable) {
        this.enabled = uniformBoolean(shader, variable + ".enabled");
        this.spaceMatrix = uniformMatrix4f(shader, variable + ".spaceMatrix");
        this.mapSize = uniformInteger(shader, variable + ".mapSize");
    }

    public static UniformShadow uniformShadow(final Shader shader, final String variable) {
        return new UniformShadow(shader, variable);
    }

    public static UniformShadow[] uniformShadow(final Shader shader, final String variable, final int count) {
        UniformShadow[] uniforms = new UniformShadow[count];
        for (int i = 0; i < count; i++) {
            uniforms[i] = uniformShadow(shader, variable + "[" + i + "]");
        }
        return uniforms;
    }

    @Override
    public void load(final Shadow value) {
        if (value != null) {
            enabled.load(true);
            spaceMatrix.load(value.spaceMatrix);
            mapSize.load(value.shadowMap.resolution.x);
        } else {
            enabled.load(false);
        }
    }
}
