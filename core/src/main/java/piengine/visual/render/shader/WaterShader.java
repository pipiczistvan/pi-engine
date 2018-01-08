package piengine.visual.render.shader;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import piengine.visual.fog.Fog;
import piengine.visual.light.Light;
import piengine.visual.shader.domain.Shader;
import piengine.visual.shader.domain.ShaderDao;

public class WaterShader extends Shader {

    private int location_modelMatrix;
    private int location_viewMatrix;
    private int location_projectionMatrix;
    private int location_reflectionTexture;
    private int location_refractionTexture;
    private int location_depthTexture;
    private int location_cameraPosition;
    private int location_waveFactor;
    private int location_lightPosition;
    private int location_lightColor;
    private int location_lightBias;
    private int location_fogColor;
    private int location_fogDensity;
    private int location_fogGradient;

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
        location_lightPosition = getUniformLocation("lightPosition");
        location_lightColor = getUniformLocation("lightColor");
        location_lightBias = getUniformLocation("lightBias");
        location_fogColor = getUniformLocation("fogColor");
        location_fogDensity = getUniformLocation("fogDensity");
        location_fogGradient = getUniformLocation("fogGradient");
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

    public WaterShader loadLight(final Light light) {
        loadUniform(location_lightPosition, light.getPosition());
        loadUniform(location_lightColor, light.color);
        loadUniform(location_lightBias, light.bias);
        return this;
    }

    public WaterShader loadFog(final Fog fog) {
        loadUniform(location_fogColor, fog.color);
        loadUniform(location_fogDensity, fog.density);
        loadUniform(location_fogGradient, fog.gradient);

        return this;
    }
}
