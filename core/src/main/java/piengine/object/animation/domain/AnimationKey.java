package piengine.object.animation.domain;

import piengine.core.base.domain.Key;

public class AnimationKey implements Key {

    public final String file;

    public AnimationKey(final String file) {
        this.file = file;
    }
}
