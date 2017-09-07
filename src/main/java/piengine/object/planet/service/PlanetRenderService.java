package piengine.object.planet.service;

import piengine.object.planet.shader.PlanetShader;
import piengine.visual.render.domain.RenderContext;
import piengine.visual.render.domain.RenderType;
import piengine.visual.render.interpreter.RenderInterpreter;
import piengine.visual.render.service.AbstractRenderService;
import piengine.visual.shader.service.ShaderService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import static org.lwjgl.opengl.GL11.GL_BACK;
import static piengine.visual.render.domain.RenderType.RENDER_PLANET;

@Component
public class PlanetRenderService extends AbstractRenderService<PlanetShader> {

    private final RenderInterpreter renderInterpreter;

    @Wire
    public PlanetRenderService(final ShaderService shaderService,
                               final RenderInterpreter renderInterpreter) {
        super(shaderService);

        this.renderInterpreter = renderInterpreter;
    }

    @Override
    protected PlanetShader createShader(final ShaderService shaderService) {
        return shaderService.supply("planetShader").castTo(PlanetShader.class);
    }

    @Override
    public void render(final RenderContext context) {
        renderInterpreter.setCullFace(GL_BACK);
        renderInterpreter.setDepthTest(true);

        shader.start()
                .loadLight(context.light)
                .loadProjectionMatrix(context.camera.projection)
                .loadViewMatrix(context.camera.view);
        shader.loadModelMatrix(context.planet.getModelMatrix());
        renderInterpreter.render(context.planet.mesh.dao);
        shader.stop();
    }

    @Override
    public RenderType getType() {
        return RENDER_PLANET;
    }
}
