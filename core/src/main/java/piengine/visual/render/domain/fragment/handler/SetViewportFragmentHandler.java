package piengine.visual.render.domain.fragment.handler;

import org.joml.Vector2i;
import piengine.visual.render.domain.RenderContext;
import piengine.visual.render.domain.fragment.domain.RenderFragmentType;
import puppeteer.annotation.premade.Component;

import static piengine.visual.render.domain.fragment.domain.RenderFragmentType.SET_VIEWPORT;

@Component
public class SetViewportFragmentHandler extends FragmentHandler<Vector2i> {

    @Override
    public void handle(final RenderContext context, final Vector2i viewport) {
        context.viewport.set(viewport);
    }

    @Override
    public RenderFragmentType getType() {
        return SET_VIEWPORT;
    }

}
