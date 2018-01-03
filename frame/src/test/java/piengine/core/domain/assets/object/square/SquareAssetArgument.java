package piengine.core.domain.assets.object.square;

import org.joml.Vector2i;
import piengine.object.asset.domain.AssetArgument;
import piengine.visual.framebuffer.domain.FrameBuffer;

public class SquareAssetArgument implements AssetArgument {

    final Vector2i viewport;
    final FrameBuffer frameBuffer;

    public SquareAssetArgument(final Vector2i viewport, final FrameBuffer frameBuffer) {
        this.viewport = viewport;
        this.frameBuffer = frameBuffer;
    }
}
