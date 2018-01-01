package piengine.object.model.domain;

import org.joml.Vector4f;
import piengine.visual.texture.domain.Texture;

public class ModelAttribute {

    public Texture texture;
    public Vector4f color;

    public ModelAttribute(final Texture texture, final Vector4f color) {
        this.texture = texture;
        this.color = color;
    }
}
