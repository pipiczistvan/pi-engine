package piengine.visual.render.service;

import piengine.core.base.api.Initializable;
import piengine.core.base.api.Service;
import piengine.visual.render.domain.RenderContext;
import piengine.visual.render.domain.RenderType;
import piengine.visual.shader.domain.Shader;
import piengine.visual.shader.service.ShaderService;

public abstract class AbstractRenderService<S extends Shader> implements Service, Initializable {

    private final ShaderService shaderService;
    protected S shader;

    public AbstractRenderService(final ShaderService shaderService) {
        this.shaderService = shaderService;
    }

    @Override
    public void initialize() {
        this.shader = createShader(shaderService);
    }

    protected abstract S createShader(final ShaderService shaderService);

    public abstract void render(final RenderContext context);

    public abstract RenderType getType();

}
