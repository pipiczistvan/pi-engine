package piengine.object.canvas.domain;

import piengine.core.base.type.color.Color;
import piengine.object.entity.domain.Entity;
import piengine.visual.texture.domain.Texture;

import static piengine.core.utils.ColorUtils.WHITE;

public class CanvasKey {

    public final Entity parent;
    public final Texture texture;
    public final Color color;

    public CanvasKey(final Entity parent, final Texture texture, final Color color) {
        this.parent = parent;
        this.texture = texture;
        this.color = color;
    }

    public CanvasKey(final Entity parent, final Texture texture) {
        this(parent, texture, WHITE);
    }

    public CanvasKey(final Entity parent, final Color color) {
        this(parent, null, color);
    }

    public CanvasKey(final Entity parent) {
        this(parent, null, WHITE);
    }
}
