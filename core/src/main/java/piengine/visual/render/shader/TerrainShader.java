package piengine.visual.render.shader;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import piengine.core.base.type.color.Color;
import piengine.core.utils.ColorUtils;
import piengine.core.utils.VectorUtils;
import piengine.visual.fog.Fog;
import piengine.visual.light.Light;
import piengine.visual.shader.domain.Shader;
import piengine.visual.shader.domain.ShaderDao;

import java.util.List;

public class TerrainShader extends Shader {

    private int location_modelMatrix;
    private int location_viewMatrix;
    private int location_projectionMatrix;
    private int location_color;
    private int location_clippingPlane;
    private int location_fogColor;
    private int location_fogDensity;
    private int location_fogGradient;
    private int[] location_lights_position;
    private int[] location_lights_color;

    public TerrainShader(final ShaderDao dao) {
        super(dao);
    }

    @Override
    protected void getUniformLocations() {
        location_modelMatrix = getUniformLocation("modelMatrix");
        location_viewMatrix = getUniformLocation("viewMatrix");
        location_projectionMatrix = getUniformLocation("projectionMatrix");
        location_color = getUniformLocation("color");
        location_clippingPlane = getUniformLocation("clippingPlane");
        location_fogColor = getUniformLocation("fogColor");
        location_fogDensity = getUniformLocation("fogDensity");
        location_fogGradient = getUniformLocation("fogGradient");

        location_lights_position = new int[MAX_LIGHTS];
        location_lights_color = new int[MAX_LIGHTS];
        for (int i = 0; i < MAX_LIGHTS; i++) {
            location_lights_position[i] = getUniformLocation("lights[" + i + "].position");
            location_lights_color[i] = getUniformLocation("lights[" + i + "].color");
        }
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

    public TerrainShader loadLights(final List<Light> lights) {
        int lightCount = lights.size();

        for (int i = 0; i < MAX_LIGHTS; i++) {
            Vector3f position;
            Color color;
            if (i < lightCount) {
                Light light = lights.get(i);
                position = light.getPosition();
                color = light.color;
            } else {
                position = VectorUtils.ZERO;
                color = ColorUtils.BLACK;
            }
            loadUniform(location_lights_position[i], position);
            loadUniform(location_lights_color[i], color);
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
