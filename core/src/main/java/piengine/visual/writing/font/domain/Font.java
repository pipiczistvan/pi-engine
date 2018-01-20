package piengine.visual.writing.font.domain;

import piengine.visual.texture.domain.Texture;

public class Font extends Texture<FontDao> {

    public final FontData data;

    public Font(final FontDao dao, final FontData data) {
        super(dao, null);

        this.data = data;
    }
}
