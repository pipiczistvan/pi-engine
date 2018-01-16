package piengine.visual.render.shader;

import org.joml.Matrix4f;
import piengine.visual.fog.Fog;
import piengine.visual.shader.domain.Shader;
import piengine.visual.shader.domain.ShaderDao;
import piengine.visual.shader.domain.uniform.UniformColor;
import piengine.visual.shader.domain.uniform.UniformMatrix4f;

public class SkyboxShader extends Shader {

    private final UniformMatrix4f projectionMatrix = new UniformMatrix4f(this, "projectionMatrix");
    private final UniformMatrix4f viewMatrix = new UniformMatrix4f(this, "viewMatrix");
    private final UniformColor fogColor = new UniformColor(this, "fogColor");

    public SkyboxShader(final ShaderDao dao) {
        super(dao);
    }

    public SkyboxShader start() {
        startShader();

        return this;
    }

    public SkyboxShader stop() {
        stopShader();

        return this;
    }

    public SkyboxShader loadProjectionMatrix(final Matrix4f value) {
        projectionMatrix.load(value);

        return this;
    }

    public SkyboxShader loadViewMatrix(final Matrix4f value) {
        viewMatrix.load(value);

        return this;
    }

    public SkyboxShader loadFog(final Fog fog) {
        fogColor.load(fog.color);

        return this;
    }
}
