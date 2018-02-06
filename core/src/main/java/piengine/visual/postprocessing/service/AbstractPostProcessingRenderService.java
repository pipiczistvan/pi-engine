package piengine.visual.postprocessing.service;

import piengine.core.base.api.Initializable;
import piengine.core.base.api.Service;
import piengine.io.interpreter.shader.Shader;
import piengine.io.interpreter.vertexarray.VertexArray;
import piengine.io.loader.glsl.domain.GlslDto;
import piengine.io.loader.glsl.loader.GlslLoader;
import piengine.visual.postprocessing.domain.context.PostProcessingEffectContext;
import piengine.visual.render.interpreter.RenderInterpreter;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static piengine.io.interpreter.vertexarray.VertexAttribute.VERTEX;

public abstract class AbstractPostProcessingRenderService<S extends Shader, C extends PostProcessingEffectContext> extends AbstractPostProcessingService<C> implements Service, Initializable {

    private static final float[] VERTICES = {-0.5f, 0.5f, -0.5f, -0.5f, 0.5f, 0.5f, 0.5f, -0.5f};

    private final RenderInterpreter renderInterpreter;
    private final GlslLoader glslLoader;

    protected S shader;
    private VertexArray canvasVertexArray;

    public AbstractPostProcessingRenderService(final RenderInterpreter renderInterpreter, final GlslLoader glslLoader) {
        this.renderInterpreter = renderInterpreter;
        this.glslLoader = glslLoader;
    }

    @Override
    public void initialize() {
        this.shader = createShader();
        this.canvasVertexArray = createVertexArray();
    }

    protected void draw() {
        canvasVertexArray.bind().enableAttributes();
        renderInterpreter.drawElements(GL_TRIANGLES, canvasVertexArray.vertexCount);
        canvasVertexArray.disableAttributes().unbind();
    }

    protected S createShader(final String file, final Class<S> shaderClass) {
        GlslDto glsl = glslLoader.load("postprocessing/" + file);

        return Shader.newInstance(shaderClass, glsl);
    }

    protected abstract S createShader();

    private VertexArray createVertexArray() {
        VertexArray vertexArray = new VertexArray(VERTICES.length / 2);

        return vertexArray
                .bind()
                .attachVertexBuffer(VERTEX, VERTICES, 2)
                .unbind();
    }
}
