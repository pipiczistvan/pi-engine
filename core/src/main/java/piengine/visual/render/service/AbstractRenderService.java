package piengine.visual.render.service;

import piengine.core.base.api.Initializable;
import piengine.core.base.api.Service;
import piengine.io.interpreter.shader.Shader;
import piengine.io.interpreter.vertexarray.VertexArray;
import piengine.io.loader.glsl.domain.GlslDto;
import piengine.io.loader.glsl.loader.GlslLoader;
import piengine.visual.render.domain.config.RenderConfig;
import piengine.visual.render.domain.context.RenderContext;
import piengine.visual.render.interpreter.RenderInterpreter;

import static org.lwjgl.opengl.GL11.GL_TRIANGLE_STRIP;
import static org.lwjgl.opengl.GL31.glDrawArraysInstanced;

public abstract class AbstractRenderService<S extends Shader, C extends RenderContext> implements Service, Initializable {

    protected final RenderInterpreter renderInterpreter;
    private final GlslLoader glslLoader;

    protected S shader;
    private RenderConfig renderConfig;

    public AbstractRenderService(final RenderInterpreter renderInterpreter, final GlslLoader glslLoader) {
        this.renderInterpreter = renderInterpreter;
        this.glslLoader = glslLoader;
    }

    @Override
    public void initialize() {
        this.shader = createShader();
        this.renderConfig = createRenderConfig();
    }

    public void process(final C context) {
        preConfig();
        shader.start();
        render(context);
        shader.stop();
    }

    protected void draw(final VertexArray vao) {
        vao.bind().enableAttributes();
        switch (renderConfig.renderFunction) {
            case DRAW_ARRAYS:
                renderInterpreter.drawArrays(renderConfig.drawMode, vao.vertexCount);
                break;
            case DRAW_ELEMENTS:
                renderInterpreter.drawElements(renderConfig.drawMode, vao.vertexCount);
                break;
        }
        vao.disableAttributes().unbind();
    }

    protected void drawInstanced(final VertexArray vao, final int primCount) {
        vao.bind().enableAttributes();
        glDrawArraysInstanced(GL_TRIANGLE_STRIP, 0, vao.vertexCount, primCount);
        vao.disableAttributes().unbind();
    }

    protected S createShader(final String file, final Class<S> shaderClass) {
        GlslDto glsl = glslLoader.load(file);

        return Shader.newInstance(shaderClass, glsl);
    }

    protected abstract S createShader();

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
