package piengine.visual.render.shader;

import org.joml.Matrix4f;
import org.joml.Vector4f;
import piengine.core.base.type.color.Color;
import piengine.core.utils.ColorUtils;
import piengine.visual.fog.Fog;
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
    private int location_clippingPlane;
    private int location_fogColor;
    private int location_fogDensity;
    private int location_fogGradient;

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
        location_clippingPlane = getUniformLocation("clippingPlane");
        location_fogColor = getUniformLocation("fogColor");
        location_fogDensity = getUniformLocation("fogDensity");
        location_fogGradient = getUniformLocation("fogGradient");
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

    public WorldShader loadClippingPlane(final Vector4f clippingPlane) {
        loadUniform(location_clippingPlane, clippingPlane);

        return this;
    }

    public WorldShader loadFog(final Fog fog) {
        loadUniform(location_fogColor, fog.color);
        loadUniform(location_fogDensity, fog.density);
        loadUniform(location_fogGradient, fog.gradient);

        return this;
    }
}
