package piengine.object.animatedmodel.domain;

import org.joml.Matrix4f;
import piengine.core.base.domain.Domain;
import piengine.core.base.domain.Entity;
import piengine.io.interpreter.texture.Texture;
import piengine.io.interpreter.vertexarray.VertexArray;
import piengine.io.loader.dae.domain.Joint;

public class AnimatedModel extends Entity implements Domain {

    public final VertexArray vao;
    public final Texture texture;
    public final Joint rootJoint;
    private final int jointCount;

    public AnimatedModel(final Entity parent, final VertexArray vao, final Texture texture, final Joint rootJoint, final int jointCount) {
        super(parent);
        this.vao = vao;
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
