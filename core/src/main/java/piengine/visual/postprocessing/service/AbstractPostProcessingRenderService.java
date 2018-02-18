package piengine.visual.postprocessing.service;

import piengine.core.base.api.Initializable;
import piengine.core.base.api.Service;
import piengine.object.mesh.domain.Mesh;
import piengine.object.mesh.domain.MeshKey;
import piengine.object.mesh.service.MeshService;
import piengine.visual.postprocessing.domain.context.PostProcessingEffectContext;
import piengine.visual.render.interpreter.RenderInterpreter;
import piengine.visual.shader.domain.Shader;
import piengine.visual.shader.domain.ShaderKey;
import piengine.visual.shader.service.ShaderService;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;

public abstract class AbstractPostProcessingRenderService<S extends Shader, C extends PostProcessingEffectContext> extends AbstractPostProcessingService<C> implements Service, Initializable {

    private final RenderInterpreter renderInterpreter;
    private final ShaderService shaderService;
    private final MeshService meshService;

    private Mesh canvas;
    protected S shader;

    public AbstractPostProcessingRenderService(final RenderInterpreter renderInterpreter, final ShaderService shaderService,
                                               final MeshService meshService) {
        this.renderInterpreter = renderInterpreter;
        this.shaderService = shaderService;
        this.meshService = meshService;
    }

    @Override
    public void initialize() {
        this.shader = createShader();
        this.canvas = meshService.supply(new MeshKey("canvas"));
    }

    protected void draw() {
        canvas.getDao().vertexArray.bind().enableAttributes();

        renderInterpreter.drawElements(GL_TRIANGLES, canvas.getDao().vertexArray.vertexCount);

        canvas.getDao().vertexArray.disableAttributes().unbind();
    }

    protected S createShader(final String file, final Class<S> shaderClass) {
        return shaderService.supply(new ShaderKey("postprocessing/" + file)).castTo(shaderClass);
    }

    protected abstract S createShader();
}
