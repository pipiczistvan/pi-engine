package piengine.object.model.service;

import piengine.object.model.domain.Model;
import piengine.object.model.shader.PlaneModelShader;
import piengine.visual.render.domain.RenderContext;
import piengine.visual.render.domain.RenderType;
import piengine.visual.render.interpreter.RenderInterpreter;
import piengine.visual.render.service.AbstractRenderService;
import piengine.visual.shader.service.ShaderService;
import piengine.visual.texture.service.TextureService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import static org.lwjgl.opengl.GL11.GL_NONE;
import static piengine.visual.render.domain.RenderType.RENDER_PLANE_MODEL;

@Component
public class PlaneModelRenderService extends AbstractRenderService<PlaneModelShader> {

    private final TextureService textureService;
    private final RenderInterpreter renderInterpreter;

    @Wire
    public PlaneModelRenderService(final ShaderService shaderService,
                                   final TextureService textureService,
                                   final RenderInterpreter renderInterpreter) {
        super(shaderService);

        this.textureService = textureService;
        this.renderInterpreter = renderInterpreter;
    }

    @Override
    protected PlaneModelShader createShader(final ShaderService shaderService) {
        return shaderService.supply("planeModelShader").castTo(PlaneModelShader.class);
    }

    @Override
    public void render(final RenderContext context) {
        renderInterpreter.setCullFace(GL_NONE);
        renderInterpreter.setDepthTest(false);

        shader.start();
        textureService.bind(context.texture);
        for (Model model : context.models) {
            shader.loadModelMatrix(model.getModelMatrix());
            renderInterpreter.render(model.mesh.dao);
        }
        shader.stop();
    }

    @Override
    public RenderType getType() {
        return RENDER_PLANE_MODEL;
    }

}
