package piengine.visual.render.domain.fragment.handler;

import org.joml.Vector4f;
import piengine.visual.render.domain.RenderContext;
import piengine.visual.render.domain.fragment.domain.RenderFragmentType;
import puppeteer.annotation.premade.Component;

import static piengine.visual.render.domain.fragment.domain.RenderFragmentType.SET_COLOR;

@Component
public class SetColorFragmentHandler extends FragmentHandler<Vector4f> {

    @Override
    public void handle(final RenderContext context, final Vector4f color) {
        context.color = color;
    }

    @Override
    public RenderFragmentType getType() {
        return SET_COLOR;
    }

}
