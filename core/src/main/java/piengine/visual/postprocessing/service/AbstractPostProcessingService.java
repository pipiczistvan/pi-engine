package piengine.visual.postprocessing.service;

import piengine.core.base.api.Initializable;
import piengine.core.base.api.Service;
import piengine.object.mesh.domain.Mesh;
import piengine.object.mesh.domain.MeshKey;
import piengine.object.mesh.service.MeshService;
import piengine.visual.postprocessing.domain.Effective;
import piengine.visual.postprocessing.domain.PostProcessingEffectContext;
import piengine.visual.render.interpreter.RenderInterpreter;
import piengine.visual.shader.domain.Shader;
import piengine.visual.shader.service.ShaderService;
import piengine.visual.texture.domain.Texture;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;

public abstract class AbstractPostProcessingService<S extends Shader, C extends PostProcessingEffectContext> implements Service, Initializable, Effective {

    private final RenderInterpreter renderInterpreter;
    private final ShaderService shaderService;
    private final MeshService meshService;

    private Mesh canvas;
    protected S shader;

    public AbstractPostProcessingService(final RenderInterpreter renderInterpreter, final ShaderService shaderService,
                                         final MeshService meshService) {
        this.renderInterpreter = renderInterpreter;
        this.shaderService = shaderService;
        this.meshService = meshService;
    }

    @Override
    public void initialize() {
        this.shader = createShader(shaderService);
        this.canvas = meshService.supply(new MeshKey("canvas"));
    }

    public void process(final C context) {
        render(context);
    }

    protected void draw() {
        renderInterpreter.bindVertexArray(canvas.getDao().vaoId);
        renderInterpreter.enableVertexAttribArray(canvas.getDao().getVertexAttribs());
        renderInterpreter.drawElements(GL_TRIANGLES, canvas.getDao().vertexCount);
        renderInterpreter.disableVertexAttribArray(canvas.getDao().getVertexAttribs());
        renderInterpreter.unbindVertexArray();
    }

    public abstract C createContext(final Texture inputTexture);

    protected abstract void render(final C context);

    protected abstract S createShader(final ShaderService shaderService);
}
