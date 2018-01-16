package piengine.visual.shader.domain.uniform;


import piengine.visual.shader.domain.Shader;
import piengine.visual.shader.service.ShaderService;

public abstract class Uniform<T> {

    private final String variable;
    protected ShaderService shaderService;
    protected int location;

    public Uniform(final Shader shader, final String variable) {
        shader.registerUniform(this);
        this.variable = variable;
    }

    public void initialize(final Shader shader, final ShaderService shaderService) {
        this.shaderService = shaderService;
        this.location = shaderService.getUniformLocation(shader, variable);
    }

    public abstract void load(final T value);
}
