package piengine.visual.render.shader;

import org.joml.Matrix4f;
import org.joml.Vector4f;
import piengine.visual.fog.Fog;
import piengine.visual.light.Light;
import piengine.visual.shader.domain.Shader;
import piengine.visual.shader.domain.ShaderDao;

public class TerrainShader extends Shader {

    private int location_modelMatrix;
    private int location_viewMatrix;
    private int location_projectionMatrix;
    private int location_lightEnabled;
    private int location_lightPosition;
    private int location_lightColor;
    private int location_color;
    private int location_clippingPlane;
    private int location_fogColor;
    private int location_fogDensity;
    private int location_fogGradient;

    public TerrainShader(final ShaderDao dao) {
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
        location_clippingPlane = getUniformLocation("clippingPlane");
        location_fogColor = getUniformLocation("fogColor");
        location_fogDensity = getUniformLocation("fogDensity");
        location_fogGradient = getUniformLocation("fogGradient");
    }

    public TerrainShader start() {
        startShader();

        return this;
    }

    public TerrainShader stop() {
        stopShader();

        return this;
    }

    public TerrainShader loadModelMatrix(final Matrix4f modelMatrix) {
        loadUniform(location_modelMatrix, modelMatrix);

        return this;
    }

    public TerrainShader loadViewMatrix(final Matrix4f viewMatrix) {
        loadUniform(location_viewMatrix, viewMatrix);

        return this;
    }

    public TerrainShader loadProjectionMatrix(final Matrix4f projectionMatrix) {
        loadUniform(location_projectionMatrix, projectionMatrix);

        return this;
    }

    public TerrainShader loadLight(final Light light) {
        if (light != null) {
            loadUniform(location_lightEnabled, true);
            loadUniform(location_lightPosition, light.getPosition());
            loadUniform(location_lightColor, light.color);
        } else {
            loadUniform(location_lightEnabled, false);
        }

        return this;
    }

    public TerrainShader loadColor(final Vector4f color) {
        if (color != null) {
            loadUniform(location_color, color);
        } else {
            loadUniform(location_color, new Vector4f(1));
        }

        return this;
    }

    public TerrainShader loadClippingPlane(final Vector4f clippingPlane) {
        loadUniform(location_clippingPlane, clippingPlane);

        return this;
    }

    public TerrainShader loadFog(final Fog fog) {
        loadUniform(location_fogColor, fog.color);
        loadUniform(location_fogDensity, fog.density);
        loadUniform(location_fogGradient, fog.gradient);

        return this;
    }
}
