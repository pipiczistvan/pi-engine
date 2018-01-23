package piengine.object.animation.domain;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class JointTransform {

    private final Vector3f position;
    private final Quaternion rotation;

    public JointTransform(final Vector3f position, final Quaternion rotation) {
        this.position = position;
        this.rotation = rotation;
    }

    public Matrix4f getLocalTransform() {
        Matrix4f matrix = new Matrix4f();
        matrix.translate(position);
        matrix.mul(rotation.toRotationMatrix(), matrix);
        return matrix;
    }

    public static JointTransform interpolate(final JointTransform frameA, final JointTransform frameB, final float progression) {
        Vector3f pos = interpolate(frameA.position, frameB.position, progression);
        Quaternion rot = Quaternion.interpolate(frameA.rotation, frameB.rotation, progression);
        return new JointTransform(pos, rot);
    }

    private static Vector3f interpolate(final Vector3f start, final Vector3f end, final float progression) {
        float x = start.x + (end.x - start.x) * progression;
        float y = start.y + (end.y - start.y) * progression;
        float z = start.z + (end.z - start.z) * progression;
        return new Vector3f(x, y, z);
    }
}
