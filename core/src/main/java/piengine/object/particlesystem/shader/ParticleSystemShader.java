package piengine.object.particlesystem.shader;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import piengine.visual.shader.domain.Shader;
import piengine.visual.shader.domain.ShaderDao;
import piengine.visual.shader.domain.uniform.UniformMatrix4f;
import piengine.visual.shader.domain.uniform.UniformVector2f;

import static piengine.visual.shader.domain.uniform.UniformMatrix4f.uniformMatrix4f;
import static piengine.visual.shader.domain.uniform.UniformVector2f.uniformVector2f;

public class ParticleSystemShader extends Shader {

    private final UniformMatrix4f projectionMatrix = uniformMatrix4f(this, "projectionMatrix");
    private final UniformMatrix4f modelViewMatrix = uniformMatrix4f(this, "modelViewMatrix");
    private final UniformVector2f textureOffsetCurrent = uniformVector2f(this, "textureOffsetCurrent");
    private final UniformVector2f textureOffsetNext = uniformVector2f(this, "textureOffsetNext");
    private final UniformVector2f textureCoordsInfo = uniformVector2f(this, "textureCoordsInfo");

    public ParticleSystemShader(final ShaderDao dao) {
        super(dao);
    }

    public ParticleSystemShader loadProjectionMatrix(final Matrix4f value) {
        projectionMatrix.load(value);

        return this;
    }

    public ParticleSystemShader loadModelViewMatrix(final Matrix4f value) {
        modelViewMatrix.load(value);

        return this;
    }

    public ParticleSystemShader loadTextureOffsetCurrent(final Vector2f value) {
        textureOffsetCurrent.load(value);

        return this;
    }

    public ParticleSystemShader loadTextureOffsetNext(final Vector2f value) {
        textureOffsetNext.load(value);

        return this;
    }

    public ParticleSystemShader loadTextureCoordsInfo(final Vector2f value) {
        textureCoordsInfo.load(value);

        return this;
    }
}
