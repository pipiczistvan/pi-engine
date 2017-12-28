package piengine.object.model.shader;

import org.joml.Matrix4f;
import org.joml.Vector4f;
import piengine.visual.shader.domain.Shader;
import piengine.visual.shader.domain.ShaderDao;

public class PlaneModelShader extends Shader {

    private int location_modelMatrix;
    private int location_color;
    private int location_textureEnabled;

    public PlaneModelShader(final ShaderDao dao) {
        super(dao);
    }

    @Override
    protected void getUniformLocations() {
        location_modelMatrix = getUniformLocation("modelMatrix");
        location_color = getUniformLocation("color");
        location_textureEnabled = getUniformLocation("textureEnabled");
    }

    public PlaneModelShader start() {
        startShader();

        return this;
    }

    public PlaneModelShader stop() {
        stopShader();

        return this;
    }

    public PlaneModelShader loadModelMatrix(final Matrix4f modelMatrix) {
        loadUniform(location_modelMatrix, modelMatrix);

        return this;
    }

    public PlaneModelShader loadColor(final Vector4f color) {
        if (color != null) {
            loadUniform(location_color, color);
        } else {
            loadUniform(location_color, new Vector4f(1));
        }

        return this;
    }

    public PlaneModelShader loadTextureEnabled(final boolean value) {
        loadUniform(location_textureEnabled, value);

        return this;
    }

}
