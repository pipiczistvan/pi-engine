package piengine.visual.render.domain.fragment.handler;

import org.joml.Vector2i;
import piengine.visual.render.domain.RenderContext;
import piengine.visual.render.domain.fragment.domain.RenderFragmentType;
import puppeteer.annotation.premade.Component;

import static piengine.visual.render.domain.fragment.domain.RenderFragmentType.SET_VIEW_PORT;

@Component
public class SetViewPortFragmentHandler extends FragmentHandler<Vector2i> {

    @Override
    public void handle(final RenderContext context, final Vector2i viewPort) {
        context.viewPort.set(viewPort);
    }

    @Override
    public RenderFragmentType getType() {
        return SET_VIEW_PORT;
    }

}
