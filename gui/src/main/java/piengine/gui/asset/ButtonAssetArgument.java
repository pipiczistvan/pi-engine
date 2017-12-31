package piengine.gui.asset;

import org.joml.Vector2i;
import piengine.core.base.event.Event;
import piengine.object.asset.domain.AssetArgument;

class ButtonAssetArgument extends AssetArgument {

    final String defaultImageName;
    final String hoverImageName;
    final String pressImageName;
    final Vector2i viewport;
    final Event onClickEvent;

    ButtonAssetArgument(final String defaultImageName, final String hoverImageName, final String pressImageName, final Vector2i viewport, final Event onClickEvent) {
        this.defaultImageName = defaultImageName;
        this.hoverImageName = hoverImageName;
        this.pressImageName = pressImageName;
        this.viewport = viewport;
        this.onClickEvent = onClickEvent;
    }
}
