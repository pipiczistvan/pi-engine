package piengine.visual.render.shader;

import org.joml.Matrix4f;
import piengine.visual.shader.domain.Shader;
import piengine.visual.shader.domain.ShaderDao;

public class SkyboxShader extends Shader {

    private int location_projectionMatrix;
    private int location_viewMatrix;

    public SkyboxShader(final ShaderDao dao) {
        super(dao);
    }

    @Override
    protected void getUniformLocations() {
        location_projectionMatrix = getUniformLocation("projectionMatrix");
        location_viewMatrix = getUniformLocation("viewMatrix");
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
        Matrix4f matrix = new Matrix4f(viewMatrix);
        matrix.m30(0);
        matrix.m31(0);
        matrix.m32(0);

        loadUniform(location_viewMatrix, matrix);

        return this;
    }
}
