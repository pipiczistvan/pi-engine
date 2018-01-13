package piengine.visual.render.shader;

import org.joml.Matrix4f;
import org.joml.Vector4f;
import piengine.visual.fog.Fog;
import piengine.visual.light.Light;
import piengine.visual.shader.domain.Shader;
import piengine.visual.shader.domain.ShaderDao;

import java.util.List;

public class TerrainShader extends Shader {

    private int location_viewMatrix;
    private int location_projectionMatrix;
    private int location_clippingPlane;
    private int location_fogColor;
    private int location_fogDensity;
    private int location_fogGradient;
    private int location_shadowMapSpaceMatrix;
    private int location_shadowMap;
    private int[] location_lights_position;
    private int[] location_lights_color;
    private int[] location_lights_attenuation;

    public TerrainShader(final ShaderDao dao) {
        super(dao);
    }

    @Override
    protected void getUniformLocations() {
        location_viewMatrix = getUniformLocation("viewMatrix");
        location_projectionMatrix = getUniformLocation("projectionMatrix");
        location_clippingPlane = getUniformLocation("clippingPlane");
        location_fogColor = getUniformLocation("fogColor");
        location_fogDensity = getUniformLocation("fogDensity");
        location_fogGradient = getUniformLocation("fogGradient");
        location_shadowMapSpaceMatrix = getUniformLocation("shadowMapSpaceMatrix");
        location_shadowMap = getUniformLocation("shadowMap");

        location_lights_position = new int[MAX_LIGHTS];
        location_lights_color = new int[MAX_LIGHTS];
        location_lights_attenuation = new int[MAX_LIGHTS];
        for (int i = 0; i < MAX_LIGHTS; i++) {
            location_lights_position[i] = getUniformLocation("lights[" + i + "].position");
            location_lights_color[i] = getUniformLocation("lights[" + i + "].color");
            location_lights_attenuation[i] = getUniformLocation("lights[" + i + "].attenuation");
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
            Light light = i < lightCount ? lights.get(i) : new Light(null);
            loadUniform(location_lights_position[i], light.getPosition());
            loadUniform(location_lights_color[i], light.getColor());
            loadUniform(location_lights_attenuation[i], light.getAttenuation());
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

    public TerrainShader loadShadowMapSpaceMatrix(final Matrix4f shadowMapSpaceMatrix) {
        loadUniform(location_shadowMapSpaceMatrix, shadowMapSpaceMatrix);

        return this;
    }

    public TerrainShader loadTextureUnits() {
        loadUniform(location_shadowMap, 0);

        return this;
    }
}
