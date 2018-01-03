package piengine.visual.writing.text.domain;

import org.joml.Vector4f;
import piengine.object.entity.domain.Entity;
import piengine.object.entity.domain.EntityDomain;
import piengine.visual.writing.font.domain.Font;

public class Text extends EntityDomain<TextDao> {

    public final Font font;
    public final Vector4f color;

    public Text(final Font font, final Vector4f color, final Entity parent, final TextDao dao) {
        super(parent, dao);

        this.font = font;
        this.color = color;
    }
}
