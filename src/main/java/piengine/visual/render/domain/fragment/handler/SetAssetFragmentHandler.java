package piengine.visual.render.domain.fragment.handler;

import piengine.object.asset.domain.Asset;
import piengine.visual.render.domain.RenderContext;
import piengine.visual.render.domain.fragment.domain.RenderFragmentType;
import puppeteer.annotation.premade.Component;

import static piengine.visual.render.domain.fragment.domain.RenderFragmentType.SET_ASSET;

@Component
public class SetAssetFragmentHandler extends FragmentHandler<Asset> {

    @Override
    public void handle(final RenderContext context, final Asset asset) {
        context.asset = asset;
    }

    @Override
    public RenderFragmentType getType() {
        return SET_ASSET;
    }

}
