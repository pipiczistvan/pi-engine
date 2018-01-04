package piengine.visual.render.shader;

import org.joml.Matrix4f;
import piengine.core.base.type.color.Color;
import piengine.core.utils.ColorUtils;
import piengine.visual.light.Light;
import piengine.visual.shader.domain.Shader;
import piengine.visual.shader.domain.ShaderDao;

public class WorldShader extends Shader {

    private int location_modelMatrix;
    private int location_viewMatrix;
    private int location_projectionMatrix;
    private int location_lightEnabled;
    private int location_lightPosition;
    private int location_lightColor;
    private int location_color;
    private int location_textureEnabled;

    public WorldShader(final ShaderDao dao) {
        super(dao);
    }

    @Override
    protected void getUniformLocations() {
        location_modelMatrix = getUniformLocation("modelMatrix");
        location_viewMatrix = getUniformLocation("viewMatrix");
        location_projectionMatrix = getUniformLocation("projectionMatrix");
        location_lightEnabled = getUniformLocation("lightEnabled");
        location_lightPosition = getUniformLocation("lightPosition");
        location_lightColor = getUniformLocation("lightColor");
        location_color = getUniformLocation("color");
        location_textureEnabled = getUniformLocation("textureEnabled");
    }

    public WorldShader start() {
        startShader();

        return this;
    }

    public WorldShader stop() {
        stopShader();

        return this;
    }

    public WorldShader loadModelMatrix(final Matrix4f modelMatrix) {
        loadUniform(location_modelMatrix, modelMatrix);

        return this;
    }

    public WorldShader loadViewMatrix(final Matrix4f viewMatrix) {
        loadUniform(location_viewMatrix, viewMatrix);

        return this;
    }

    public WorldShader loadProjectionMatrix(final Matrix4f projectionMatrix) {
        loadUniform(location_projectionMatrix, projectionMatrix);

        return this;
    }

    public WorldShader loadLight(final Light light) {
        if (light != null) {
            loadUniform(location_lightEnabled, true);
            loadUniform(location_lightPosition, light.getPosition());
            loadUniform(location_lightColor, light.color);
        } else {
            loadUniform(location_lightEnabled, false);
        }

        return this;
    }

    public WorldShader loadColor(final Color color) {
        if (color != null) {
            loadUniform(location_color, color);
        } else {
            loadUniform(location_color, ColorUtils.WHITE);
        }

        return this;
    }

    public WorldShader loadTextureEnabled(final boolean value) {
        loadUniform(location_textureEnabled, value);

        return this;
    }
}
