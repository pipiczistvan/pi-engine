package piengine.visual.image.domain;

import piengine.core.base.domain.Key;

public class ImageKey implements Key {

    public final String file;

    public ImageKey(final String file) {
        this.file = file;
    }
}
