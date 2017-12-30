package piengine.core.domain.assets.object.square;

import piengine.object.asset.domain.AssetArgument;
import piengine.visual.framebuffer.domain.FrameBuffer;

public class SquareAssetArgument extends AssetArgument {

    final FrameBuffer frameBuffer;

    SquareAssetArgument(final FrameBuffer frameBuffer) {
        this.frameBuffer = frameBuffer;
    }
}
