package piengine.object.animatedmodel.domain;

import piengine.core.base.domain.ResourceData;

public class AnimatedModelData implements ResourceData {

    public final SkeletonData joints;
    public final GeometryData mesh;

    public AnimatedModelData(final SkeletonData joints, final GeometryData mesh) {
        this.joints = joints;
        this.mesh = mesh;
    }
}
