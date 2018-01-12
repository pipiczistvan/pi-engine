package piengine.core.domain.assets.object.square;

import org.joml.Vector2i;
import piengine.object.asset.domain.AssetArgument;
import piengine.visual.framebuffer.domain.Framebuffer;

public class SquareAssetArgument implements AssetArgument {

    final Vector2i viewport;
    final Framebuffer framebuffer;

    public SquareAssetArgument(final Vector2i viewport, final Framebuffer framebuffer) {
        this.viewport = viewport;
        this.framebuffer = framebuffer;
    }
}
