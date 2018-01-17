package piengine.visual.texture.domain;

import piengine.core.base.domain.ResourceData;

import java.nio.ByteBuffer;

public class TextureData implements ResourceData {

    public final int width;
    public final int height;
    public final ByteBuffer buffer;

    public TextureData(int width, int height, ByteBuffer buffer) {
        this.width = width;
        this.height = height;
        this.buffer = buffer;
    }
}
