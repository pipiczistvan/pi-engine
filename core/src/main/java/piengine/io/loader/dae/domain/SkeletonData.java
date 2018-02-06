package piengine.io.loader.dae.domain;

public class SkeletonData {

    public final int jointCount;
    public final JointData headJoint;

    public SkeletonData(final int jointCount, final JointData headJoint) {
        this.jointCount = jointCount;
        this.headJoint = headJoint;
    }
}
