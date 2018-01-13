package piengine.object.model.domain;

import piengine.core.base.type.color.Color;
import piengine.object.entity.domain.Entity;
import piengine.visual.texture.domain.Texture;

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
}
