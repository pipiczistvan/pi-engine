package piengine.visual.render.shader;

import org.joml.Matrix4f;
import piengine.visual.fog.Fog;
import piengine.visual.shader.domain.Shader;
import piengine.visual.shader.domain.ShaderDao;

public class SkyboxShader extends Shader {

    private int location_projectionMatrix;
    private int location_viewMatrix;
    private int location_fogColor;

    public SkyboxShader(final ShaderDao dao) {
        super(dao);
    }

    @Override
    protected void getUniformLocations() {
        location_projectionMatrix = getUniformLocation("projectionMatrix");
        location_viewMatrix = getUniformLocation("viewMatrix");
        location_fogColor = getUniformLocation("fogColor");
    }

    public SkyboxShader start() {
        startShader();

        return this;
    }

    public SkyboxShader stop() {
        stopShader();

        return this;
    }

    public SkyboxShader loadProjectionMatrix(final Matrix4f projectionMatrix) {
        loadUniform(location_projectionMatrix, projectionMatrix);

        return this;
    }

    public SkyboxShader loadViewMatrix(final Matrix4f viewMatrix) {
        loadUniform(location_viewMatrix, viewMatrix);

        return this;
    }

    public SkyboxShader loadFog(final Fog fog) {
        loadUniform(location_fogColor, fog.color);

        return this;
    }
}
