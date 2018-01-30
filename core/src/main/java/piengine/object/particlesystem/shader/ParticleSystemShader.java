package piengine.object.particlesystem.shader;

import org.joml.Matrix4f;
import piengine.visual.shader.domain.Shader;
import piengine.visual.shader.domain.ShaderDao;
import piengine.visual.shader.domain.uniform.UniformFloat;
import piengine.visual.shader.domain.uniform.UniformMatrix4f;

import static piengine.visual.shader.domain.uniform.UniformFloat.uniformFloat;
import static piengine.visual.shader.domain.uniform.UniformMatrix4f.uniformMatrix4f;

public class ParticleSystemShader extends Shader {

    private final UniformMatrix4f projectionMatrix = uniformMatrix4f(this, "projectionMatrix");
    private final UniformFloat numberOfRows = uniformFloat(this, "numberOfRows");

    public ParticleSystemShader(final ShaderDao dao) {
        super(dao);
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
