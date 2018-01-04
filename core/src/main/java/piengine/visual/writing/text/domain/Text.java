package piengine.visual.writing.text.domain;

import piengine.core.base.type.color.Color;
import piengine.object.entity.domain.Entity;
import piengine.object.entity.domain.EntityDomain;
import piengine.visual.writing.font.domain.Font;

public class Text extends EntityDomain<TextDao> {

    public final Font font;
    public final Color color;

    public Text(final Font font, final Color color, final Entity parent, final TextDao dao) {
        super(parent, dao);

        this.font = font;
        this.color = color;
    }
}
