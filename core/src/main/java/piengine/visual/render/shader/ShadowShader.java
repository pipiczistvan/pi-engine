package piengine.visual.render.shader;

import org.joml.Matrix4f;
import piengine.visual.shader.domain.Shader;
import piengine.visual.shader.domain.ShaderDao;

public class ShadowShader extends Shader {

    private int location_transformationMatrix;

    public ShadowShader(final ShaderDao dao) {
        super(dao);
    }

    @Override
    protected void getUniformLocations() {
        location_transformationMatrix = getUniformLocation("transformationMatrix");
    }

    public ShadowShader start() {
        startShader();

        return this;
    }

    public ShadowShader stop() {
        stopShader();

        return this;
    }

    public ShadowShader loadTransformationMatrix(final Matrix4f transformationMatrix) {
        loadUniform(location_transformationMatrix, transformationMatrix);

        return this;
    }
}
