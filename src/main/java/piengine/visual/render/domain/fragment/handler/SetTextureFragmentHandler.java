package piengine.visual.render.domain.fragment.handler;

import piengine.visual.render.domain.RenderContext;
import piengine.visual.render.domain.fragment.domain.RenderFragmentType;
import piengine.visual.texture.domain.Texture;
import puppeteer.annotation.premade.Component;

import static piengine.visual.render.domain.fragment.domain.RenderFragmentType.SET_TEXTURE;

@Component
public class SetTextureFragmentHandler extends FragmentHandler<Texture> {

    @Override
    public void handle(final RenderContext context, final Texture texture) {
        context.texture = texture;
    }

    @Override
    public RenderFragmentType getType() {
        return SET_TEXTURE;
    }

}
