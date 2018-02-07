package piengine.visual.writing.text.domain;

import org.joml.Vector2f;
import piengine.core.base.type.color.Color;
import piengine.object.entity.domain.Entity;
import piengine.object.entity.domain.EntityDomain;
import piengine.visual.writing.font.domain.Font;

public class Text extends EntityDomain<TextDao> {

    public final Font font;
    public final Color color;
    public final float width;
    public final float edge;
    public final float borderWidth;
    public final float borderEdge;
    public final Color outlineColor;
    public final Vector2f offset;

    public Text(final Entity parent, final TextDao dao, final Font font,
                final Color color, final float width, final float edge,
                final float borderWidth, final float borderEdge, final Color outlineColor,
                final Vector2f offset) {
        super(parent, dao);

        this.font = font;
        this.color = color;
        this.width = width;
        this.edge = edge;
        this.borderWidth = borderWidth;
        this.borderEdge = borderEdge;
        this.outlineColor = outlineColor;
        this.offset = offset;
    }
}
