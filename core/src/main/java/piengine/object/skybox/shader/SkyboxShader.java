package piengine.object.skybox.shader;

import org.joml.Matrix4f;
import piengine.visual.fog.Fog;
import piengine.visual.shader.domain.Shader;
import piengine.visual.shader.domain.ShaderDao;
import piengine.visual.shader.domain.uniform.UniformBoolean;
import piengine.visual.shader.domain.uniform.UniformColor;
import piengine.visual.shader.domain.uniform.UniformMatrix4f;

import static piengine.visual.shader.domain.uniform.UniformBoolean.uniformBoolean;
import static piengine.visual.shader.domain.uniform.UniformColor.uniformColor;
import static piengine.visual.shader.domain.uniform.UniformMatrix4f.uniformMatrix4f;

public class SkyboxShader extends Shader {

    private final UniformMatrix4f projectionMatrix = uniformMatrix4f(this, "projectionMatrix");
    private final UniformMatrix4f viewMatrix = uniformMatrix4f(this, "viewMatrix");
    private final UniformColor fogColor = uniformColor(this, "fog.color");
    private final UniformBoolean fogEnabled = uniformBoolean(this, "fog.enabled");

    public SkyboxShader(final ShaderDao dao) {
        super(dao);
    }

    public SkyboxShader loadProjectionMatrix(final Matrix4f value) {
        projectionMatrix.load(value);

        return this;
    }

    public SkyboxShader loadViewMatrix(final Matrix4f value) {
        viewMatrix.load(value);

        return this;
    }

    public SkyboxShader loadFog(final Fog value) {
        if (value != null) {
            fogEnabled.load(true);
            fogColor.load(value.color);
        } else {
            fogEnabled.load(false);
        }

        return this;
    }
}
