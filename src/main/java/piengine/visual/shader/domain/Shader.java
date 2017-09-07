package piengine.visual.shader.domain;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import piengine.core.base.domain.Domain;
import piengine.core.base.exception.PIEngineException;
import piengine.visual.shader.service.ShaderService;

import java.lang.reflect.InvocationTargetException;

public class Shader extends Domain<ShaderDao> {

    private ShaderService shaderService;

    public Shader(final ShaderDao dao) {
        super(dao);
    }

    public void initialize(final ShaderService shaderService) {
        this.shaderService = shaderService;
        getUniformLocations();
    }

    protected void getUniformLocations() {
    }

    protected int getUniformLocation(String variable) {
        return shaderService.getUniformLocation(this, variable);
    }

    protected void startShader() {
        shaderService.start(this);
    }

    protected void stopShader() {
        shaderService.stop();
    }

    protected void loadUniform(int location, Matrix4f value) {
        shaderService.loadUniform(location, value);
    }

    protected void loadUniform(int location, Vector3f value) {
        shaderService.loadUniform(location, value);
    }

    protected void loadUniform(int location, Vector2f value) {
        shaderService.loadUniform(location, value);
    }

    protected void loadUniform(int location, float value) {
        shaderService.loadUniform(location, value);
    }

    protected void loadUniform(int location, int value) {
        shaderService.loadUniform(location, value);
    }

    protected void loadUniform(int location, boolean value) {
        shaderService.loadUniform(location, value);
    }

    public <T extends Shader> T castTo(final Class<T> shaderClass) {
        try {
            T shader = shaderClass.getConstructor(ShaderDao.class).newInstance(dao);
            shader.initialize(shaderService);
            return shader;
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new PIEngineException("Could not cast shader to shader class %s!", shaderClass.getName());
        }
    }

}
