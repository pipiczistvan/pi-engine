package piengine.visual.writing.text.domain;

import piengine.core.base.domain.ResourceData;

public class TextData implements ResourceData {

    public final float[] vertices;
    public final float[] textureCoords;

    public TextData(final float[] vertices, final float[] textureCoords) {
        this.vertices = vertices;
        this.textureCoords = textureCoords;
    }

}
