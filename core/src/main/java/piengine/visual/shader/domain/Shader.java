package piengine.visual.shader.domain;

import piengine.core.base.domain.Domain;
import piengine.core.base.exception.PIEngineException;
import piengine.visual.shader.domain.uniform.Uniform;
import piengine.visual.shader.domain.uniform.UniformBoolean;
import piengine.visual.shader.domain.uniform.UniformColor;
import piengine.visual.shader.domain.uniform.UniformFloat;
import piengine.visual.shader.domain.uniform.UniformInteger;
import piengine.visual.shader.domain.uniform.UniformMatrix4f;
import piengine.visual.shader.domain.uniform.UniformVector2f;
import piengine.visual.shader.domain.uniform.UniformVector3f;
import piengine.visual.shader.service.ShaderService;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class Shader implements Domain<ShaderDao> {

    // todo: temporary
    protected static final int MAX_LIGHTS = 4;

    private ShaderService shaderService;
    private final ShaderDao dao;
    private final List<Uniform> uniforms;

    public Shader(final ShaderDao dao) {
        this.dao = dao;
        this.uniforms = new ArrayList<>();
    }

    public void initialize(final ShaderService shaderService) {
        this.shaderService = shaderService;
        getUniformLocations();
    }

    public void registerUniform(final Uniform uniform) {
        this.uniforms.add(uniform);
    }

    protected void getUniformLocations() {
        for (Uniform uniform : uniforms) {
            uniform.initialize(this, shaderService);
        }
    }

    protected int getUniformLocation(String variable) {
        return shaderService.getUniformLocation(this, variable);
    }

    protected UniformVector2f[] uniformVector2fArray(final String struct, final String variable, final int count) {
        UniformVector2f[] uniformArray = new UniformVector2f[count];
        for (int i = 0; i < count; i++) {
            uniformArray[i] = new UniformVector2f(this, struct + "[" + i + "]." + variable);
        }

        return uniformArray;
    }

    protected UniformVector3f[] uniformVector3fArray(final String struct, final String variable, final int count) {
        UniformVector3f[] uniformArray = new UniformVector3f[count];
        for (int i = 0; i < count; i++) {
            uniformArray[i] = new UniformVector3f(this, struct + "[" + i + "]." + variable);
        }

        return uniformArray;
    }

    protected UniformColor[] uniformColorArray(final String struct, final String variable, final int count) {
        UniformColor[] uniformArray = new UniformColor[count];
        for (int i = 0; i < count; i++) {
            uniformArray[i] = new UniformColor(this, struct + "[" + i + "]." + variable);
        }

        return uniformArray;
    }

    protected UniformFloat[] uniformFloatArray(final String struct, final String variable, final int count) {
        UniformFloat[] uniformArray = new UniformFloat[count];
        for (int i = 0; i < count; i++) {
            uniformArray[i] = new UniformFloat(this, struct + "[" + i + "]." + variable);
        }

        return uniformArray;
    }

    protected UniformBoolean[] uniformBooleanArray(final String struct, final String variable, final int count) {
        UniformBoolean[] uniformArray = new UniformBoolean[count];
        for (int i = 0; i < count; i++) {
            uniformArray[i] = new UniformBoolean(this, struct + "[" + i + "]." + variable);
        }

        return uniformArray;
    }

    protected UniformInteger[] uniformIntegerArray(final String struct, final String variable, final int count) {
        UniformInteger[] uniformArray = new UniformInteger[count];
        for (int i = 0; i < count; i++) {
            uniformArray[i] = new UniformInteger(this, struct + "[" + i + "]." + variable);
        }

        return uniformArray;
    }

    protected UniformMatrix4f[] uniformMatrix4fArray(final String struct, final String variable, final int count) {
        UniformMatrix4f[] uniformArray = new UniformMatrix4f[count];
        for (int i = 0; i < count; i++) {
            uniformArray[i] = new UniformMatrix4f(this, struct + "[" + i + "]." + variable);
        }

        return uniformArray;
    }

    protected void startShader() {
        shaderService.start(this);
    }

    protected void stopShader() {
        shaderService.stop();
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

    @Override
    public ShaderDao getDao() {
        return dao;
    }
}
