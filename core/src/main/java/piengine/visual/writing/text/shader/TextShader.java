package piengine.visual.writing.text.shader;


import org.joml.Matrix4f;
import org.joml.Vector4f;
import piengine.visual.shader.domain.Shader;
import piengine.visual.shader.domain.ShaderDao;

public class TextShader extends Shader {

    private int location_modelMatrix;
    private int location_color;

    public TextShader(final ShaderDao dao) {
        super(dao);
    }

    @Override
    protected void getUniformLocations() {
        location_modelMatrix = super.getUniformLocation("modelMatrix");
        location_color = super.getUniformLocation("color");
    }

    public TextShader start() {
        startShader();

        return this;
    }

    public TextShader stop() {
        stopShader();

        return this;
    }


    public TextShader loadModelMatrix(final Matrix4f modelMatrix) {
        loadUniform(location_modelMatrix, modelMatrix);

        return this;
    }

    public TextShader loadColor(final Vector4f color) {
        super.loadUniform(location_color, color);

        return this;
    }

}
