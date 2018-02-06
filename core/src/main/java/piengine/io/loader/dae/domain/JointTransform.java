package piengine.io.loader.dae.domain;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import static piengine.core.utils.VectorUtils.interpolateVector3f;

public class JointTransform {

    private final Vector3f position;
    private final Quaternionf rotation;

    public JointTransform(final Vector3f position, final Quaternionf rotation) {
        this.position = position;
        this.rotation = rotation;
    }

    public Matrix4f getLocalTransform() {
        return new Matrix4f().translate(position).rotate(rotation);
    }

    public static JointTransform interpolate(final JointTransform frameA, final JointTransform frameB, final float progression) {
        Vector3f pos = interpolateVector3f(frameA.position, frameB.position, progression);
        Quaternionf rot = new Quaternionf();
        frameA.rotation.slerp(frameB.rotation, progression, rot);
        return new JointTransform(pos, rot);
    }
}
