package piengine.visual.texture.image.domain;

import piengine.core.base.domain.Domain;
import piengine.io.interpreter.texture.Texture;

public class Image extends Texture implements Domain {

    public Image(final int width, final int height) {
        super(width, height);
    }
}
