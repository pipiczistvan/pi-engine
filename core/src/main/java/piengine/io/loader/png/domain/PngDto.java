package piengine.io.loader.png.domain;

import piengine.io.loader.Dto;

import java.nio.ByteBuffer;

public class PngDto implements Dto {

    public final ByteBuffer buffer;
    public final int width;
    public final int height;
    public final int comp;

    public PngDto(final ByteBuffer buffer, final int width, final int height, final int comp) {
        this.buffer = buffer;
        this.width = width;
        this.height = height;
        this.comp = comp;
    }
}
