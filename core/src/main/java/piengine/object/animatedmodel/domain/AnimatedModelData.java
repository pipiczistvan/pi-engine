package piengine.object.animatedmodel.domain;

import piengine.core.base.domain.ResourceData;
import piengine.object.entity.domain.Entity;
import piengine.visual.texture.domain.Texture;

public class AnimatedModelData implements ResourceData {

    public final Entity parent;
    public final Texture texture;
    public final SkeletonData joints;
    public final GeometryData mesh;

    public AnimatedModelData(final Entity parent, final Texture texture, final SkeletonData joints, final GeometryData mesh) {
        this.parent = parent;
        this.texture = texture;
        this.joints = joints;
        this.mesh = mesh;
    }
}
