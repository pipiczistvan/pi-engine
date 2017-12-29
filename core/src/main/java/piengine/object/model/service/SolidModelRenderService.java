package piengine.object.model.service;

import piengine.object.model.domain.Model;
import piengine.object.model.shader.SolidModelShader;
import piengine.visual.framebuffer.service.FrameBufferService;
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

import static piengine.visual.render.domain.RenderType.RENDER_SOLID_MODEL;

@Component
public class SolidModelRenderService extends AbstractRenderService<SolidModelShader> {

    private final TextureService textureService;

    @Wire
    public SolidModelRenderService(final ShaderService shaderService,
                                   final TextureService textureService,
                                   final FrameBufferService frameBufferService,
                                   final RenderInterpreter renderInterpreter) {
        super(shaderService, frameBufferService, renderInterpreter);

        this.textureService = textureService;
    }

    @Override
    protected SolidModelShader createShader(final ShaderService shaderService) {
        return shaderService.supply("solidModelShader").castTo(SolidModelShader.class);
    }

    @Override
    protected void render(final RenderContext context) {
        shader.start()
                .loadLight(context.light)
                .loadColor(context.color)
                .loadProjectionMatrix(context.camera.projection)
                .loadViewMatrix(context.camera.view)
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
        return RENDER_SOLID_MODEL;
    }

    @Override
    protected RenderConfig createRenderConfig() {
        return RenderConfigBuilder.create()
                .build();
    }
}
