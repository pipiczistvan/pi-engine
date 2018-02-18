package piengine.visual.render.service;

import piengine.core.base.api.Initializable;
import piengine.core.base.api.Service;
import piengine.object.mesh.domain.MeshDao;
import piengine.object.particlesystem.domain.ParticleSystemDao;
import piengine.visual.render.domain.config.RenderConfig;
import piengine.visual.render.domain.context.RenderContext;
import piengine.visual.render.interpreter.RenderInterpreter;
import piengine.visual.shader.domain.Shader;
import piengine.visual.shader.service.ShaderService;

import static org.lwjgl.opengl.GL11.GL_TRIANGLE_STRIP;
import static org.lwjgl.opengl.GL31.glDrawArraysInstanced;

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
        shader.start();
        render(context);
        shader.stop();
    }

    protected void draw(final MeshDao dao) {
        dao.vertexArray.bind().enableAttributes();
        switch (renderConfig.renderFunction) {
            case DRAW_ARRAYS:
                renderInterpreter.drawArrays(renderConfig.drawMode, dao.vertexArray.vertexCount);
                break;
            case DRAW_ELEMENTS:
                renderInterpreter.drawElements(renderConfig.drawMode, dao.vertexArray.vertexCount);
                break;
        }
        dao.vertexArray.disableAttributes().unbind();
    }

    protected void drawInstanced(final ParticleSystemDao dao, final int primCount) {
        dao.vertexArray.bind().enableAttributes();
        glDrawArraysInstanced(GL_TRIANGLE_STRIP, 0, dao.vertexArray.vertexCount, primCount);
        dao.vertexArray.disableAttributes().unbind();
    }

    protected abstract S createShader(final ShaderService shaderService);

    protected abstract void render(final C context);

    protected abstract RenderConfig createRenderConfig();

    private void preConfig() {
        renderInterpreter.setDepthTest(renderConfig.depthTest);
        renderInterpreter.setDepthMask(renderConfig.depthMask);
        renderInterpreter.setBlendTest(renderConfig.blendTest);
        renderInterpreter.setCullFace(renderConfig.cullFace);
        renderInterpreter.setWireFrameMode(renderConfig.wireFrameMode);
        renderInterpreter.setClipDistance(renderConfig.clipDistance);
    }
}
