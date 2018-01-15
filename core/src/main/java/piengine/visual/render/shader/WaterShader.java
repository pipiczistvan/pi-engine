package piengine.visual.render.shader;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import piengine.visual.fog.Fog;
import piengine.visual.light.domain.Light;
import piengine.visual.shader.domain.Shader;
import piengine.visual.shader.domain.ShaderDao;
import piengine.visual.shadow.domain.Shadow;

import java.util.List;

public class WaterShader extends Shader {

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
    private int[] location_lights_attenuation;

    private int[] location_shadows_enabled;
    private int[] location_shadows_shadowMap;
    private int[] location_shadows_spaceMatrix;

    public WaterShader(final ShaderDao dao) {
        super(dao);
    }

    @Override
    protected void getUniformLocations() {
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
        location_lights_attenuation = new int[MAX_LIGHTS];
        for (int i = 0; i < MAX_LIGHTS; i++) {
            location_lights_position[i] = getUniformLocation("lights[" + i + "].position");
            location_lights_color[i] = getUniformLocation("lights[" + i + "].color");
            location_lights_bias[i] = getUniformLocation("lights[" + i + "].bias");
            location_lights_attenuation[i] = getUniformLocation("lights[" + i + "].attenuation");
        }

        location_shadows_enabled = new int[MAX_LIGHTS];
        location_shadows_shadowMap = new int[MAX_LIGHTS];
        location_shadows_spaceMatrix = new int[MAX_LIGHTS];
        for (int i = 0; i < MAX_LIGHTS; i++) {
            location_shadows_enabled[i] = getUniformLocation("shadows[" + i + "].enabled");
            location_shadows_shadowMap[i] = getUniformLocation("shadows[" + i + "].shadowMap");
            location_shadows_spaceMatrix[i] = getUniformLocation("shadows[" + i + "].spaceMatrix");
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

    public WaterShader loadViewMatrix(final Matrix4f viewMatrix) {
        loadUniform(location_viewMatrix, viewMatrix);
        return this;
    }

    public WaterShader loadProjectionMatrix(final Matrix4f projectionMatrix) {
        loadUniform(location_projectionMatrix, projectionMatrix);
        return this;
    }

    public WaterShader loadTextureUnits(final List<Shadow> shadows) {
        int textureIndex = 0;

        while (textureIndex < shadows.size() && textureIndex < MAX_LIGHTS) {
            loadUniform(location_shadows_shadowMap[textureIndex], textureIndex++);
        }
        loadUniform(location_reflectionTexture, textureIndex++);
        loadUniform(location_refractionTexture, textureIndex++);
        loadUniform(location_depthTexture, textureIndex++);
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
            Light light = i < lightCount ? lights.get(i) : new Light(null);
            loadUniform(location_lights_position[i], light.getPosition());
            loadUniform(location_lights_color[i], light.getColor());
            loadUniform(location_lights_bias[i], light.getBias());
            loadUniform(location_lights_attenuation[i], light.getAttenuation());
        }

        return this;
    }

    public WaterShader loadShadows(final List<Shadow> shadows) {
        int shadowCount = shadows.size();

        for (int i = 0; i < MAX_LIGHTS; i++) {
            if (i < shadowCount) {
                loadUniform(location_shadows_enabled[i], true);
                loadUniform(location_shadows_spaceMatrix[i], shadows.get(i).spaceMatrix);
            } else {
                loadUniform(location_shadows_enabled[i], false);
            }
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
