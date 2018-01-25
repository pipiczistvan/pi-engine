package piengine.core.utils;

import org.joml.Vector3f;

public class MathUtils {

    private MathUtils() {
    }

    public static float clamp(final float value, final float min, final float max) {
        return Math.max(Math.min(value, max), min);
    }


    public static Vector3f calculateNormal(final Vector3f vertex0, final Vector3f vertex1, final Vector3f vertex2) {
        Vector3f tangentA = new Vector3f();
        vertex1.sub(vertex0, tangentA);

        Vector3f tangentB = new Vector3f();
        vertex2.sub(vertex0, tangentB);

        Vector3f normal = new Vector3f();
        tangentA.cross(tangentB, normal);
        normal.normalize();

        return normal;
    }
}
