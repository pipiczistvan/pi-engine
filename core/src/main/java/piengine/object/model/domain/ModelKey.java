package piengine.object.model.domain;

import piengine.core.base.type.color.Color;
import piengine.object.entity.domain.Entity;
import piengine.visual.texture.domain.Texture;

import static piengine.core.utils.ColorUtils.WHITE;

public class ModelKey {

    public final Entity parent;
    public final String file;
    public final Texture texture;
    public final Color color;

    public ModelKey(final Entity parent, final String file, final Texture texture, final Color color) {
        this.parent = parent;
        this.file = file;
        this.texture = texture;
        this.color = color;
    }

    public ModelKey(final Entity parent, final String file, final Texture texture) {
        this(parent, file, texture, WHITE);
    }

    public ModelKey(final Entity parent, final String file, final Color color) {
        this(parent, file, null, color);
    }

    public ModelKey(final Entity parent, final String file) {
        this(parent, file, null, WHITE);
    }
}
