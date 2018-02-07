package piengine.visual.writing.font.domain;

import org.joml.Vector2i;
import piengine.visual.texture.domain.Texture;

public class Font extends Texture<FontDao> {

    private FontData data;

    public Font(final FontDao dao, final FontData data, final Vector2i resolution) {
        super(dao, new Vector2i(resolution));

        this.data = data;
    }

    public FontData getData() {
        return data;
    }

    public void setDao(final FontDao dao) {
        this.dao = dao;
    }

    public void setData(final FontData data) {
        this.data = data;
    }

    public void setSize(final Vector2i size) {
        this.size.set(size);
    }
}
