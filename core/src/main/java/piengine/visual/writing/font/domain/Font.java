package piengine.visual.writing.font.domain;

import piengine.visual.texture.domain.Texture;

public class Font extends Texture<FontDao> {

    public FontKey key;
    public FontData data;

    public Font(final FontDao dao, final FontKey key, final FontData data) {
        super(dao, null);

        this.key = key;
        this.data = data;
    }

    public void setDao(final FontDao dao) {
        this.dao = dao;
    }
}
