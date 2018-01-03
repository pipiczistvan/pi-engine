package piengine.visual.shader.service;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import piengine.core.base.exception.PIEngineException;
import piengine.core.base.resource.SupplierService;
import piengine.visual.shader.accessor.ShaderAccessor;
import piengine.visual.shader.domain.Shader;
import piengine.visual.shader.domain.ShaderDao;
import piengine.visual.shader.domain.ShaderData;
import piengine.visual.shader.interpreter.ShaderInterpreter;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

@Component
public class ShaderService extends SupplierService<Shader, ShaderDao, ShaderData> {

    private final ShaderInterpreter shaderInterpreter;

    @Wire
    public ShaderService(final ShaderAccessor shaderAccessor, final ShaderInterpreter shaderInterpreter) {
        super(shaderAccessor, shaderInterpreter);
        this.shaderInterpreter = shaderInterpreter;
    }

    @Override
    protected Shader createDomain(final ShaderDao dao, final ShaderData resource) {
        Shader shader = new Shader(dao);
        shader.initialize(this);

        return shader;
    }

    public void start(Shader shader) {
        shaderInterpreter.startShader(shader.getDao());
    }

    public void stop() {
        shaderInterpreter.stopShader();
    }

    public void loadUniform(final int location, final Matrix4f value) {
        shaderInterpreter.loadMatrix4(location, value);
    }

    public void loadUniform(final int location, final Vector4f value) {
        shaderInterpreter.loadVector4(location, value);
    }

    public void loadUniform(final int location, final Vector3f value) {
        shaderInterpreter.loadVector3(location, value);
    }

    public void loadUniform(final int location, final Vector2f value) {
        shaderInterpreter.loadVector2(location, value);
    }

    public void loadUniform(final int location, final float value) {
        shaderInterpreter.loadFloat(location, value);
    }

    public void loadUniform(final int location, final int value) {
        shaderInterpreter.loadInt(location, value);
    }

    public void loadUniform(final int location, final boolean value) {
        shaderInterpreter.loadBoolean(location, value);
    }

    public int getUniformLocation(final Shader shader, final String variable) {
        int location = shaderInterpreter.getUniformLocation(shader.getDao(), variable);
        if (location < 0) {
            throw new PIEngineException("Could not find uniform variable %s!", variable);
        }
        return location;
    }

}
