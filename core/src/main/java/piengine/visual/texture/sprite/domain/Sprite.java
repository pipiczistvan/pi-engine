package piengine.visual.texture.sprite.domain;

import piengine.core.base.domain.Domain;
import piengine.io.interpreter.texture.Texture;

public class Sprite extends Texture implements Domain {

    public final int numberOfRows;

    public Sprite(final int width, final int height, final int numberOfRows) {
        super(width, height);
        this.numberOfRows = numberOfRows;
    }
}
