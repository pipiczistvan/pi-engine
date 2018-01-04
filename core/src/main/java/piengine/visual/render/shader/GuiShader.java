package piengine.visual.render.shader;

import org.joml.Matrix4f;
import piengine.core.base.type.color.Color;
import piengine.core.utils.ColorUtils;
import piengine.visual.shader.domain.Shader;
import piengine.visual.shader.domain.ShaderDao;

public class GuiShader extends Shader {

    private int location_modelMatrix;
    private int location_color;
    private int location_textureEnabled;

    public GuiShader(final ShaderDao dao) {
        super(dao);
    }

    @Override
    protected void getUniformLocations() {
        location_modelMatrix = getUniformLocation("modelMatrix");
        location_color = getUniformLocation("color");
        location_textureEnabled = getUniformLocation("textureEnabled");
    }

    public GuiShader start() {
        startShader();

        return this;
    }

    public GuiShader stop() {
        stopShader();

        return this;
    }

    public GuiShader loadModelMatrix(final Matrix4f modelMatrix) {
        loadUniform(location_modelMatrix, modelMatrix);

        return this;
    }

    public GuiShader loadColor(final Color color) {
        if (color != null) {
            loadUniform(location_color, color);
        } else {
            loadUniform(location_color, ColorUtils.WHITE);
        }

        return this;
    }

    public GuiShader loadTextureEnabled(final boolean value) {
        loadUniform(location_textureEnabled, value);

        return this;
    }

}
