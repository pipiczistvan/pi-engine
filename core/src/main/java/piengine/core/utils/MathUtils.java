package piengine.core.utils;

public class MathUtils {

    private MathUtils() {
    }

    public static float clamp(final float value, final float min, final float max) {
        return Math.max(Math.min(value, max), min);
    }
}
