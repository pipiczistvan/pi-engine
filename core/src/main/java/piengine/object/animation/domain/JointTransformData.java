package piengine.object.animation.domain;

import org.joml.Matrix4f;

public class JointTransformData {

    public final String jointNameId;
    public final Matrix4f jointLocalTransform;

    public JointTransformData(final String jointNameId, final Matrix4f jointLocalTransform) {
        this.jointNameId = jointNameId;
        this.jointLocalTransform = jointLocalTransform;
    }
}
