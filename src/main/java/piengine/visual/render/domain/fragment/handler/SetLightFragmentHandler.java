package piengine.visual.render.domain.fragment.handler;

import piengine.visual.light.Light;
import piengine.visual.render.domain.RenderContext;
import piengine.visual.render.domain.fragment.domain.RenderFragmentType;
import puppeteer.annotation.premade.Component;

import static piengine.visual.render.domain.fragment.domain.RenderFragmentType.SET_LIGHT;

@Component
public class SetLightFragmentHandler extends FragmentHandler<Light> {

    @Override
    public void handle(final RenderContext context, final Light light) {
        context.light = light;
    }

    @Override
    public RenderFragmentType getType() {
        return SET_LIGHT;
    }

}
