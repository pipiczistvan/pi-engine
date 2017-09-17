package piengine.object.planet.service;

import piengine.object.planet.shader.PlanetShader;
import piengine.visual.render.domain.RenderContext;
import piengine.visual.render.domain.RenderType;
import piengine.visual.render.domain.config.RenderConfig;
import piengine.visual.render.domain.config.RenderConfigBuilder;
import piengine.visual.render.interpreter.RenderInterpreter;
import piengine.visual.render.service.AbstractRenderService;
import piengine.visual.shader.service.ShaderService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import static org.lwjgl.opengl.GL11.GL_NONE;
import static piengine.visual.render.domain.RenderType.RENDER_PLANET;

@Component
public class PlanetRenderService extends AbstractRenderService<PlanetShader> {

    @Wire
    public PlanetRenderService(final ShaderService shaderService,
                               final RenderInterpreter renderInterpreter) {
        super(shaderService, renderInterpreter);
    }

    @Override
    protected PlanetShader createShader(final ShaderService shaderService) {
        return shaderService.supply("planetShader").castTo(PlanetShader.class);
    }

    @Override
    protected void render(final RenderContext context) {
        shader.start()
                .loadLight(context.light)
                .loadProjectionMatrix(context.camera.projection)
                .loadViewMatrix(context.camera.view)
                .loadModelMatrix(context.planet.getModelMatrix());
        draw(context.planet.mesh.dao);
        shader.stop();
    }

    @Override
    public RenderType getType() {
        return RENDER_PLANET;
    }

    @Override
    protected RenderConfig createRenderConfig() {
        return RenderConfigBuilder.create()
                .withWireFrameMode(true)
                .withCullFace(GL_NONE)
                .build();
    }
}
