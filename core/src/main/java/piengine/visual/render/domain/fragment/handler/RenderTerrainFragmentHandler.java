package piengine.visual.render.domain.fragment.handler;

import piengine.object.terrain.domain.Terrain;
import piengine.visual.render.domain.context.TerrainRenderContext;
import piengine.visual.render.domain.fragment.domain.RenderFragmentType;
import piengine.visual.render.service.TerrainRenderService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import static piengine.visual.render.domain.fragment.domain.RenderFragmentType.RENDER_TERRAIN;

@Component
public class RenderTerrainFragmentHandler extends FragmentHandler<TerrainRenderContext> {

    private final TerrainRenderService renderService;

    @Wire
    public RenderTerrainFragmentHandler(final TerrainRenderService renderService) {
        this.renderService = renderService;
    }

    @Override
    public void handle(final TerrainRenderContext context) {
        renderService.process(context);
    }

    @Override
    public void validate(final TerrainRenderContext context) {
        check("terrains", notNull(context.terrains));
        for (Terrain terrain : context.terrains) {
            check("terrain", notNull(terrain));
        }
    }

    @Override
    public RenderFragmentType getType() {
        return RENDER_TERRAIN;
    }
}
