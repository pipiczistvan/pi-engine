package piengine.object.animation.domain;

import piengine.core.base.domain.Domain;
import piengine.io.loader.dae.domain.KeyFrame;

public class Animation implements Domain {

    public final float length;
    public final KeyFrame[] keyFrames;

    public Animation(final float length, final KeyFrame[] keyFrames) {
        this.length = length;
        this.keyFrames = keyFrames;
    }
}
