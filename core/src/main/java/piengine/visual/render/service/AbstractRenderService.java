package piengine.visual.render.service;

import piengine.core.base.api.Initializable;
import piengine.core.base.api.Service;
import piengine.object.mesh.domain.MeshDao;
import piengine.visual.render.domain.config.RenderConfig;
import piengine.visual.render.domain.context.RenderContext;
import piengine.visual.render.interpreter.RenderInterpreter;
import piengine.visual.shader.domain.Shader;
import piengine.visual.shader.service.ShaderService;

public abstract class AbstractRenderService<S extends Shader, C extends RenderContext> implements Service, Initializable {

    protected final RenderInterpreter renderInterpreter;
    private final ShaderService shaderService;
    protected S shader;
    private RenderConfig renderConfig;

    public AbstractRenderService(final ShaderService shaderService,
                                 final RenderInterpreter renderInterpreter) {
        this.shaderService = shaderService;
        this.renderInterpreter = renderInterpreter;
    }

    @Override
    public void initialize() {
        this.shader = createShader(shaderService);
        this.renderConfig = createRenderConfig();
    }

    public void process(final C context) {
        preConfig();
        render(context);
    }

    protected void draw(final MeshDao dao) {
        renderInterpreter.bindVertexArray(dao.vaoId);
        renderInterpreter.enableVertexAttribArray(dao.getVertexAttribs());

        switch (renderConfig.renderFunction) {
            case DRAW_ARRAYS:
                renderInterpreter.drawArrays(renderConfig.drawMode, dao.vertexCount);
                break;
            case DRAW_ELEMENTS:
                renderInterpreter.drawElements(renderConfig.drawMode, dao.vertexCount);
                break;
        }

        renderInterpreter.disableVertexAttribArray(dao.getVertexAttribs());
        renderInterpreter.unbindVertexArray();
    }

    protected abstract S createShader(final ShaderService shaderService);

    protected abstract void render(final C context);

    protected abstract RenderConfig createRenderConfig();

    private void preConfig() {
        renderInterpreter.setDepthTest(renderConfig.depthTest);
        renderInterpreter.setBlendTest(renderConfig.blendTest);
        renderInterpreter.setCullFace(renderConfig.cullFace);
        renderInterpreter.setWireFrameMode(renderConfig.wireFrameMode);
        renderInterpreter.setClipDistance(renderConfig.clipDistance);
    }
}
