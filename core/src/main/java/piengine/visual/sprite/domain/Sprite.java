package piengine.visual.sprite.domain;

import org.joml.Vector2i;
import piengine.visual.texture.domain.Texture;

public class Sprite extends Texture<SpriteDao> {

    private final int numberOfRows;

    public Sprite(final SpriteDao dao, final Vector2i size, final int numberOfRows) {
        super(dao, size);
        this.numberOfRows = numberOfRows;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }
}
