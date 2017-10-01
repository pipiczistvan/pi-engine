package piengine.planet.shader;

import org.joml.Matrix4f;
import piengine.visual.light.Light;
import piengine.visual.shader.domain.Shader;
import piengine.visual.shader.domain.ShaderDao;

public class PlanetShader extends Shader {

    private int location_modelMatrix;
    private int location_viewMatrix;
    private int location_projectionMatrix;
    private int location_lightPosition;
    private int location_lightColor;

    public PlanetShader(final ShaderDao dao) {
        super(dao);
    }

    @Override
    protected void getUniformLocations() {
        location_modelMatrix = getUniformLocation("modelMatrix");
        location_viewMatrix = getUniformLocation("viewMatrix");
        location_projectionMatrix = getUniformLocation("projectionMatrix");
        location_lightPosition = getUniformLocation("lightPosition");
        location_lightColor = getUniformLocation("lightColor");
    }

    public PlanetShader start() {
        startShader();

        return this;
    }

    public PlanetShader stop() {
        stopShader();

        return this;
    }

    public PlanetShader loadModelMatrix(final Matrix4f modelMatrix) {
        loadUniform(location_modelMatrix, modelMatrix);

        return this;
    }

    public PlanetShader loadViewMatrix(final Matrix4f viewMatrix) {
        loadUniform(location_viewMatrix, viewMatrix);

        return this;
    }

    public PlanetShader loadProjectionMatrix(final Matrix4f projectionMatrix) {
        loadUniform(location_projectionMatrix, projectionMatrix);

        return this;
    }

    public PlanetShader loadLight(final Light light) {
        loadUniform(location_lightPosition, light.getPosition());
        loadUniform(location_lightColor, light.color);

        return this;
    }

}
