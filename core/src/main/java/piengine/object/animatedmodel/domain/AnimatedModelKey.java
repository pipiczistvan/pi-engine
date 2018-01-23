package piengine.object.animatedmodel.domain;

import piengine.core.base.domain.EntityKey;
import piengine.object.entity.domain.Entity;
import piengine.visual.texture.domain.Texture;

public class AnimatedModelKey extends EntityKey {

    public final String colladaFile;
    public final Texture texture;
    public final int maxWeights;

    public AnimatedModelKey(final Entity parent, final String colladaFile, final Texture texture, final int maxWeights) {
        super(parent);
        this.colladaFile = colladaFile;
        this.texture = texture;
        this.maxWeights = maxWeights;
    }
}
