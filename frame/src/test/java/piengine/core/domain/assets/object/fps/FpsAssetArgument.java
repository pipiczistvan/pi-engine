package piengine.core.domain.assets.object.fps;

import org.joml.Vector2i;
import piengine.object.asset.domain.AssetArgument;

public class FpsAssetArgument implements AssetArgument {

    public final Vector2i viewport;

    public FpsAssetArgument(final Vector2i viewport) {
        this.viewport = viewport;
    }
}
