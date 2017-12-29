package piengine.visual.image.domain;

import piengine.visual.texture.domain.Texture;

public class Image extends Texture<ImageDao> {

    public Image(final ImageDao dao) {
        super(dao);
    }
}
