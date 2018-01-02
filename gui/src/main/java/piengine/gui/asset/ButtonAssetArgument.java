package piengine.gui.asset;

import org.joml.Vector2i;
import piengine.core.base.event.Event;
import piengine.object.asset.domain.AssetArgument;

public class ButtonAssetArgument extends AssetArgument {

    final String defaultImageName;
    final String hoverImageName;
    final String pressImageName;
    final Vector2i viewport;
    final String text;
    final Event onClickEvent;

    public ButtonAssetArgument(final String defaultImageName, final String hoverImageName, final String pressImageName, final Vector2i viewport, final String text, final Event onClickEvent) {
        this.defaultImageName = defaultImageName;
        this.hoverImageName = hoverImageName;
        this.pressImageName = pressImageName;
        this.viewport = viewport;
        this.text = text;
        this.onClickEvent = onClickEvent;
    }
}
