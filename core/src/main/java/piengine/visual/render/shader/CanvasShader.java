package piengine.visual.render.shader;

import org.joml.Matrix4f;
import piengine.core.base.type.color.Color;
import piengine.core.utils.ColorUtils;
import piengine.visual.shader.domain.Shader;
import piengine.visual.shader.domain.ShaderDao;

public class CanvasShader extends Shader {

    private int location_modelMatrix;
    private int location_color;
    private int location_textureEnabled;

    public CanvasShader(final ShaderDao dao) {
        super(dao);
    }

    @Override
    protected void getUniformLocations() {
        location_modelMatrix = getUniformLocation("modelMatrix");
        location_color = getUniformLocation("color");
        location_textureEnabled = getUniformLocation("textureEnabled");
    }

    public CanvasShader start() {
        startShader();

        return this;
    }

    public CanvasShader stop() {
        stopShader();

        return this;
    }

    public CanvasShader loadModelMatrix(final Matrix4f modelMatrix) {
        loadUniform(location_modelMatrix, modelMatrix);

        return this;
    }

    public CanvasShader loadColor(final Color color) {
        if (color != null) {
            loadUniform(location_color, color);
        } else {
            loadUniform(location_color, ColorUtils.WHITE);
        }

        return this;
    }

    public CanvasShader loadTextureEnabled(final boolean value) {
        loadUniform(location_textureEnabled, value);

        return this;
    }

}
