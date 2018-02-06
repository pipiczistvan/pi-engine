package piengine.object.particlesystem.shader;

import org.joml.Matrix4f;
import piengine.io.interpreter.shader.Shader;
import piengine.io.interpreter.shader.uniform.UniformFloat;
import piengine.io.interpreter.shader.uniform.UniformMatrix4f;
import piengine.io.loader.glsl.domain.GlslDto;

import static piengine.io.interpreter.shader.uniform.UniformFloat.uniformFloat;
import static piengine.io.interpreter.shader.uniform.UniformMatrix4f.uniformMatrix4f;

public class ParticleSystemShader extends Shader {

    private final UniformMatrix4f projectionMatrix = uniformMatrix4f(this, "projectionMatrix");
    private final UniformFloat numberOfRows = uniformFloat(this, "numberOfRows");

    public ParticleSystemShader(final GlslDto glsl) {
        super(glsl);
    }

    public ParticleSystemShader loadProjectionMatrix(final Matrix4f value) {
        projectionMatrix.load(value);

        return this;
    }

    public ParticleSystemShader loadNumberOfRows(final float value) {
        numberOfRows.load(value);

        return this;
    }
}
