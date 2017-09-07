package piengine.visual.render.domain.fragment.handler;

import piengine.object.planet.domain.Planet;
import piengine.visual.render.domain.RenderContext;
import piengine.visual.render.domain.fragment.domain.RenderFragmentType;
import puppeteer.annotation.premade.Component;

import static piengine.visual.render.domain.fragment.domain.RenderFragmentType.SET_PLANET;

@Component
public class SetPlanetFragmentHandler extends FragmentHandler<Planet> {

    @Override
    public void handle(final RenderContext context, final Planet planet) {
        context.planet = planet;
    }

    @Override
    public RenderFragmentType getType() {
        return SET_PLANET;
    }

}
