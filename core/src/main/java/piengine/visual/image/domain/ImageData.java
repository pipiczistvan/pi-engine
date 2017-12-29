package piengine.visual.image.domain;

import piengine.core.base.domain.ResourceData;

import java.nio.ByteBuffer;

public class ImageData implements ResourceData {

    public final ByteBuffer buffer;
    public final int width;
    public final int height;
    public final int comp;

    public ImageData(final ByteBuffer buffer, final int width, final int height, final int comp) {
        this.buffer = buffer;
        this.width = width;
        this.height = height;
        this.comp = comp;
    }
}
