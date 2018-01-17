package piengine.visual.image.domain;

import piengine.visual.texture.domain.TextureData;

import java.nio.ByteBuffer;

public class ImageData extends TextureData {

    public final int comp;

    public ImageData(final ByteBuffer buffer, final int width, final int height, final int comp) {
        super(width, height, buffer);
        this.comp = comp;
    }
}
