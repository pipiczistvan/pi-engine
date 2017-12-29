package piengine.visual.render.service;

import piengine.core.base.api.Initializable;
import piengine.core.base.api.Service;
import piengine.object.mesh.domain.MeshDao;
import piengine.visual.framebuffer.service.FrameBufferService;
import piengine.visual.render.domain.RenderContext;
import piengine.visual.render.domain.RenderType;
import piengine.visual.render.domain.config.RenderConfig;
import piengine.visual.render.interpreter.RenderInterpreter;
import piengine.visual.shader.domain.Shader;
import piengine.visual.shader.service.ShaderService;

import static piengine.object.mesh.domain.MeshDataType.TEXTURE_COORD;
import static piengine.object.mesh.domain.MeshDataType.VERTEX;

public abstract class AbstractRenderService<S extends Shader> implements Service, Initializable {

    private final ShaderService shaderService;
    private final FrameBufferService frameBufferService;
    private final RenderInterpreter renderInterpreter;
    protected S shader;
    private RenderConfig renderConfig;

    public AbstractRenderService(final ShaderService shaderService,
                                 final FrameBufferService frameBufferService,
                                 final RenderInterpreter renderInterpreter) {
        this.shaderService = shaderService;
        this.frameBufferService = frameBufferService;
        this.renderInterpreter = renderInterpreter;
    }

    @Override
    public void initialize() {
        this.shader = createShader(shaderService);
        this.renderConfig = createRenderConfig();
    }

    public void process(final RenderContext context) {
        preConfig(context);
        render(context);
        postConfig();
    }

    public abstract RenderType getType();

    protected void draw(final MeshDao dao) {
        renderInterpreter.bindVertexArray(dao.vaoId);
        renderInterpreter.enableVertexAttribArray(VERTEX, TEXTURE_COORD);

        switch (renderConfig.renderFunction) {
            case DRAW_ARRAYS:
                renderInterpreter.drawArrays(renderConfig.drawMode, dao.vertexCount);
                break;
            case DRAW_ELEMENTS:
                renderInterpreter.drawElements(renderConfig.drawMode, dao.vertexCount);
                break;
        }

        renderInterpreter.disableVertexAttribArray(VERTEX, TEXTURE_COORD);
        renderInterpreter.unbindVertexArray();
    }

    protected abstract S createShader(final ShaderService shaderService);

    protected abstract void render(final RenderContext context);

    protected abstract RenderConfig createRenderConfig();

    private void preConfig(final RenderContext renderContext) {
        frameBufferService.bind(renderContext.camera.frameBuffer);

        renderInterpreter.setViewport(renderContext.camera.viewport);

        renderInterpreter.setDepthTest(renderConfig.depthTest);
        renderInterpreter.setBlendTest(renderConfig.blendTest);
        renderInterpreter.setCullFace(renderConfig.cullFace);
        renderInterpreter.setWireFrameMode(renderConfig.wireFrameMode);
    }

    private void postConfig() {
        frameBufferService.unbind();
    }
}
