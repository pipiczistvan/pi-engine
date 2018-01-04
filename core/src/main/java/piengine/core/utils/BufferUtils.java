package piengine.core.utils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.BufferUtils.createFloatBuffer;
import static org.lwjgl.BufferUtils.createIntBuffer;

public class BufferUtils {

    private BufferUtils() {
    }

    public static FloatBuffer convertToFloatBuffer(final float[] data) {
        FloatBuffer buffer = createFloatBuffer(data.length);
        buffer.put(data).flip();
        return buffer;
    }

    public static IntBuffer convertToIntBuffer(final int[] data) {
        IntBuffer buffer = createIntBuffer(data.length);
        buffer.put(data).flip();
        return buffer;
    }
}
