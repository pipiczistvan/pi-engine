package piengine.visual.image.domain;

import org.joml.Vector2i;
import piengine.visual.texture.domain.Texture;

public class Image extends Texture<ImageDao> {

    public Image(final ImageDao dao, final Vector2i size) {
        super(dao, size);
    }
}
