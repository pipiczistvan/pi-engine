package piengine.object.model.domain;

import piengine.core.base.type.color.Color;
import piengine.visual.texture.domain.Texture;

public class ModelAttribute {

    public Texture texture;
    public Color color;

    public ModelAttribute(final Texture texture, final Color color) {
        this.texture = texture;
        this.color = color;
    }
}
