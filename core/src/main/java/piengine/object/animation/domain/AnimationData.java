package piengine.object.animation.domain;

import piengine.core.base.domain.ResourceData;

public class AnimationData implements ResourceData {

    public final float lengthSeconds;
    public final KeyFrameData[] keyFrames;

    public AnimationData(final float lengthSeconds, final KeyFrameData[] keyFrames) {
        this.lengthSeconds = lengthSeconds;
        this.keyFrames = keyFrames;
    }
}
