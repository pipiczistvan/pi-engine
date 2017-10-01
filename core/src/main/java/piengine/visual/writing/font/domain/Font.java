package piengine.visual.writing.font.domain;

import piengine.core.base.domain.Domain;

public class Font extends Domain<FontDao> {

    public final FontData data;

    public Font(final FontDao dao, final FontData data) {
        super(dao);

        this.data = data;
    }

}
