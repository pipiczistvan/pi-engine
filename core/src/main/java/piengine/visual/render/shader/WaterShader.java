package piengine.visual.render.shader;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import piengine.core.base.type.color.Color;
import piengine.core.utils.ColorUtils;
import piengine.core.utils.VectorUtils;
import piengine.visual.fog.Fog;
import piengine.visual.light.Light;
import piengine.visual.shader.domain.Shader;
import piengine.visual.shader.domain.ShaderDao;

import java.util.List;

public class WaterShader extends Shader {

    private int location_modelMatrix;
    private int location_viewMatrix;
    private int location_projectionMatrix;
    private int location_reflectionTexture;
    private int location_refractionTexture;
    private int location_depthTexture;
    private int location_cameraPosition;
    private int location_waveFactor;
    private int location_fogColor;
    private int location_fogDensity;
    private int location_fogGradient;
    private int[] location_lights_position;
    private int[] location_lights_color;
    private int[] location_lights_bias;

    public WaterShader(final ShaderDao dao) {
        super(dao);
    }

    @Override
    protected void getUniformLocations() {
        location_modelMatrix = getUniformLocation("modelMatrix");
        location_viewMatrix = getUniformLocation("viewMatrix");
        location_projectionMatrix = getUniformLocation("projectionMatrix");
        location_reflectionTexture = getUniformLocation("reflectionTexture");
        location_refractionTexture = getUniformLocation("refractionTexture");
        location_depthTexture = getUniformLocation("depthTexture");
        location_cameraPosition = getUniformLocation("cameraPosition");
        location_waveFactor = getUniformLocation("waveFactor");
        location_fogColor = getUniformLocation("fogColor");
        location_fogDensity = getUniformLocation("fogDensity");
        location_fogGradient = getUniformLocation("fogGradient");

        location_lights_position = new int[MAX_LIGHTS];
        location_lights_color = new int[MAX_LIGHTS];
        location_lights_bias = new int[MAX_LIGHTS];
        for (int i = 0; i < MAX_LIGHTS; i++) {
            location_lights_position[i] = getUniformLocation("lights[" + i + "].position");
            location_lights_color[i] = getUniformLocation("lights[" + i + "].color");
            location_lights_bias[i] = getUniformLocation("lights[" + i + "].bias");
        }
    }

    public WaterShader start() {
        startShader();
        return this;
    }

    public WaterShader stop() {
        stopShader();
        return this;
    }

    public WaterShader loadModelMatrix(final Matrix4f modelMatrix) {
        loadUniform(location_modelMatrix, modelMatrix);
        return this;
    }

    public WaterShader loadViewMatrix(final Matrix4f viewMatrix) {
        loadUniform(location_viewMatrix, viewMatrix);
        return this;
    }

    public WaterShader loadProjectionMatrix(final Matrix4f projectionMatrix) {
        loadUniform(location_projectionMatrix, projectionMatrix);
        return this;
    }

    public WaterShader loadTextureUnits() {
        loadUniform(location_reflectionTexture, 0);
        loadUniform(location_refractionTexture, 1);
        loadUniform(location_depthTexture, 2);
        return this;
    }

    public WaterShader loadCameraPosition(final Vector3f position) {
        loadUniform(location_cameraPosition, position);
        return this;
    }

    public WaterShader loadWaveFactor(final float waveFactor) {
        loadUniform(location_waveFactor, waveFactor);
        return this;
    }

    public WaterShader loadLights(final List<Light> lights) {
        int lightCount = lights.size();

        for (int i = 0; i < MAX_LIGHTS; i++) {
            Vector3f position;
            Color color;
            Vector2f bias;
            if (i < lightCount) {
                Light light = lights.get(i);
                position = light.getPosition();
                color = light.color;
                bias = light.bias;
            } else {
                position = VectorUtils.ZERO;
                color = ColorUtils.BLACK;
                bias = new Vector2f(0);
            }
            loadUniform(location_lights_position[i], position);
            loadUniform(location_lights_color[i], color);
            loadUniform(location_lights_bias[i], bias);
        }

        return this;
    }

    public WaterShader loadFog(final Fog fog) {
        loadUniform(location_fogColor, fog.color);
        loadUniform(location_fogDensity, fog.density);
        loadUniform(location_fogGradient, fog.gradient);

        return this;
    }
}
