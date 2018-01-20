package piengine.core.utils;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.List;

public class VectorUtils {

    public static final Vector3f RIGHT = new Vector3f(1, 0, 0);
    public static final Vector3f UP = new Vector3f(0, 1, 0);
    public static final Vector3f FORWARD = new Vector3f(0, 0, 1);
    public static final Vector3f ZERO = new Vector3f(0, 0, 0);
    private VectorUtils() {
    }

    public static float[] vector2fToFloatArray(final List<Vector2f> vectors) {
        float[] array = new float[vectors.size() * 2];

        int pointer = 0;
        for (Vector2f vector : vectors) {
            array[pointer++] = vector.x;
            array[pointer++] = vector.y;
        }

        return array;
    }

    public static float[] vector3fToFloatArray(final List<Vector3f> vectors) {
        float[] array = new float[vectors.size() * 3];

        int pointer = 0;
        for (Vector3f vector : vectors) {
            array[pointer++] = vector.x;
            array[pointer++] = vector.y;
            array[pointer++] = vector.z;
        }

        return array;
    }

    public static float[] vector4fToFloatArray(final List<Vector4f> vectors) {
        float[] array = new float[vectors.size() * 4];

        int pointer = 0;
        for (Vector4f vector : vectors) {
            array[pointer++] = vector.x;
            array[pointer++] = vector.y;
            array[pointer++] = vector.z;
            array[pointer++] = vector.w;
        }

        return array;
    }
}
