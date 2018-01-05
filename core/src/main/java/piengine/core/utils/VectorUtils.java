package piengine.core.utils;

import org.joml.Vector3f;

import java.util.List;

public class VectorUtils {

    private VectorUtils() {
    }

    public static final Vector3f RIGHT = new Vector3f(1, 0, 0);
    public static final Vector3f UP = new Vector3f(0, 1, 0);
    public static final Vector3f FORWARD = new Vector3f(0, 0, 1);

    public static float[] convertListToArray(final List<Vector3f> vectors) {
        float[] array = new float[vectors.size() * 3];

        int pointer = 0;
        for (Vector3f vector : vectors) {
            array[pointer++] = vector.x;
            array[pointer++] = vector.y;
            array[pointer++] = vector.z;
        }

        return array;
    }
}
