package piengine.object.animatedmodel.domain;

import org.joml.Matrix4f;
import piengine.object.entity.domain.Entity;
import piengine.object.entity.domain.EntityDomain;
import piengine.visual.texture.domain.Texture;

public class AnimatedModel extends EntityDomain<AnimatedModelDao> {

    public final Texture texture;
    public final Joint rootJoint;
    private final int jointCount;

    public AnimatedModel(final Entity parent, final AnimatedModelDao dao, final Texture texture, final Joint rootJoint, final int jointCount) {
        super(parent, dao);

        this.texture = texture;
        this.rootJoint = rootJoint;
        this.jointCount = jointCount;

        this.rootJoint.calcInverseBindTransform(new Matrix4f());
    }

    public Matrix4f[] getJointTransforms() {
        Matrix4f[] jointMatrices = new Matrix4f[jointCount];
        addJointsToArray(rootJoint, jointMatrices);
        return jointMatrices;
    }

    private void addJointsToArray(final Joint headJoint, final Matrix4f[] jointMatrices) {
        jointMatrices[headJoint.index] = headJoint.getAnimatedTransform();
        for (Joint childJoint : headJoint.children) {
            addJointsToArray(childJoint, jointMatrices);
        }
    }
}
