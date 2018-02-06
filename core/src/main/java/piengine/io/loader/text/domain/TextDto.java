package piengine.io.loader.text.domain;

import piengine.io.loader.Dto;

public class TextDto implements Dto {

    public final float[] vertices;
    public final float[] textureCoords;

    public TextDto(final float[] vertices, final float[] textureCoords) {
        this.vertices = vertices;
        this.textureCoords = textureCoords;
    }
}
