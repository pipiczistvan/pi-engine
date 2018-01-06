package piengine.visual.render.service;

import piengine.object.model.domain.Model;
import piengine.visual.render.domain.config.RenderConfig;
import piengine.visual.render.domain.config.RenderConfigBuilder;
import piengine.visual.render.domain.fragment.domain.RenderGuiPlanContext;
import piengine.visual.render.interpreter.RenderInterpreter;
import piengine.visual.render.shader.GuiShader;
import piengine.visual.shader.service.ShaderService;
import piengine.visual.texture.service.TextureService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import static org.lwjgl.opengl.GL11.GL_NONE;

@Component
public class GuiRenderService extends AbstractRenderService<GuiShader, RenderGuiPlanContext> {

    private final TextureService textureService;

    @Wire
    public GuiRenderService(final ShaderService shaderService,
                            final TextureService textureService,
                            final RenderInterpreter renderInterpreter) {
        super(shaderService, renderInterpreter);

        this.textureService = textureService;
    }

    @Override
    protected GuiShader createShader(final ShaderService shaderService) {
        return shaderService.supply("guiShader").castTo(GuiShader.class);
    }

    @Override
    protected void render(final RenderGuiPlanContext context) {
        renderInterpreter.setViewport(context.viewport);

        shader.start();

        for (Model model : context.models) {
            shader.loadModelMatrix(model.getModelMatrix())
                    .loadColor(model.attribute.color);

            if (model.attribute.texture != null) {
                shader.loadTextureEnabled(true);
                textureService.bind(model.attribute.texture);
            } else {
                shader.loadTextureEnabled(false);
            }

            draw(model.mesh.getDao());
        }

        shader.stop();
    }

    @Override
    protected RenderConfig createRenderConfig() {
        return RenderConfigBuilder.create()
                .withDepthTest(false)
                .withCullFace(GL_NONE)
                .build();
    }
}
