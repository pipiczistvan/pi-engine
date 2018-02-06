package piengine.visual.writing.font.domain;

import org.joml.Vector2i;

public class FontKey {

    public final String fontFile;
    public final Vector2i resolution;

    public FontKey(final String fontFile, final Vector2i resolution) {
        this.fontFile = fontFile;
        this.resolution = resolution;
    }
}
