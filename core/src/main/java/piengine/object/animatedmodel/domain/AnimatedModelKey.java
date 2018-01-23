package piengine.object.animatedmodel.domain;

import piengine.core.base.domain.Key;

public class AnimatedModelKey implements Key {

    public final String file;
    public final int maxWeights;

    public AnimatedModelKey(final String file, final int maxWeights) {
        this.file = file;
        this.maxWeights = maxWeights;
    }
}
