package piengine.visual.shader.domain;

import piengine.core.base.domain.Domain;
import piengine.core.base.exception.PIEngineException;
import piengine.visual.shader.domain.uniform.Uniform;
import piengine.visual.shader.service.ShaderService;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class Shader implements Domain<ShaderDao> {

    private final ShaderDao dao;
    private final List<Uniform> uniforms;
    private ShaderService shaderService;

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

    public void start() {
        shaderService.start(this);
    }

    public void stop() {
        shaderService.stop();
    }

    private void getUniformLocations() {
        for (Uniform uniform : uniforms) {
            uniform.initialize(this, shaderService);
        }
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
