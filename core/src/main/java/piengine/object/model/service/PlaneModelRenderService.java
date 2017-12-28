package piengine.object.model.service;

import piengine.object.model.domain.Model;
import piengine.object.model.shader.PlaneModelShader;
import piengine.visual.render.domain.RenderContext;
import piengine.visual.render.domain.RenderType;
import piengine.visual.render.domain.config.RenderConfig;
import piengine.visual.render.domain.config.RenderConfigBuilder;
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

    @Wire
    public PlaneModelRenderService(final ShaderService shaderService,
                                   final TextureService textureService,
                                   final RenderInterpreter renderInterpreter) {
        super(shaderService, renderInterpreter);

        this.textureService = textureService;
    }

    @Override
    protected PlaneModelShader createShader(final ShaderService shaderService) {
        return shaderService.supply("planeModelShader").castTo(PlaneModelShader.class);
    }

    @Override
    protected void render(final RenderContext context) {
        shader.start()
                .loadColor(context.color)
                .loadTextureEnabled(context.texture != null);

        if (context.texture != null) {
            textureService.bind(context.texture);
        }

        for (Model model : context.models) {
            shader.loadModelMatrix(model.getModelMatrix());
            draw(model.mesh.dao);
        }
        shader.stop();
    }

    @Override
    public RenderType getType() {
        return RENDER_PLANE_MODEL;
    }

    @Override
    protected RenderConfig createRenderConfig() {
        return RenderConfigBuilder.create()
                .withDepthTest(false)
                .withCullFace(GL_NONE)
                .build();
    }

}
