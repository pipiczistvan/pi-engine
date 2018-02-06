package piengine.object.skybox.service;

import org.lwjgl.opengl.GL13;
import piengine.io.loader.glsl.loader.GlslLoader;
import piengine.object.skybox.shader.SkyboxShader;
import piengine.visual.render.domain.config.RenderConfig;
import piengine.visual.render.domain.config.RenderConfigBuilder;
import piengine.visual.render.domain.fragment.domain.RenderWorldPlanContext;
import piengine.visual.render.interpreter.RenderInterpreter;
import piengine.visual.render.service.AbstractRenderService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import static piengine.visual.render.domain.config.RenderFunction.DRAW_ARRAYS;

@Component
public class SkyboxRenderService extends AbstractRenderService<SkyboxShader, RenderWorldPlanContext> {

    @Wire
    public SkyboxRenderService(final RenderInterpreter renderInterpreter, final GlslLoader glslLoader) {
        super(renderInterpreter, glslLoader);
    }

    @Override
    protected SkyboxShader createShader() {
        return createShader("skyboxShader", SkyboxShader.class);
    }

    @Override
    public void process(final RenderWorldPlanContext context) {
        if (context.skybox != null) {
            super.process(context);
        }
    }

    @Override
    protected void render(final RenderWorldPlanContext context) {
        renderInterpreter.setViewport(context.viewport);

        shader.loadProjectionMatrix(context.currentCamera.getProjection())
                .loadViewMatrix(context.skybox.getView(context.currentCamera))
                .loadFog(context.fog);

        context.skybox.getCubeImage().bind(GL13.GL_TEXTURE0);
        draw(context.skybox.getVao());
    }

    @Override
    protected RenderConfig createRenderConfig() {
        return RenderConfigBuilder.create()
                .withRenderFunction(DRAW_ARRAYS)
                .build();
    }
}
