package piengine.object.model.shader;

import org.joml.Matrix4f;
import piengine.visual.shader.domain.Shader;
import piengine.visual.shader.domain.ShaderDao;

public class PlaneModelShader extends Shader {

    private int location_modelMatrix;

    public PlaneModelShader(final ShaderDao dao) {
        super(dao);
    }

    @Override
    protected void getUniformLocations() {
        location_modelMatrix = getUniformLocation("modelMatrix");
    }

    public PlaneModelShader start() {
        startShader();

        return this;
    }

    public PlaneModelShader stop() {
        stopShader();

        return this;
    }

    public PlaneModelShader loadModelMatrix(Matrix4f modelMatrix) {
        loadUniform(location_modelMatrix, modelMatrix);

        return this;
    }

}
