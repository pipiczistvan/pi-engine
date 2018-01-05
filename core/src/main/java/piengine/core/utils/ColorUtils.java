package piengine.core.utils;

import piengine.core.base.type.color.Color;

import java.util.List;

public class ColorUtils {

    public static final Color WHITE = new Color(1);
    public static final Color BLACK = new Color(0);

    private ColorUtils() {
    }

    public static Color interpolateColors(Color color1, Color color2, float blend) {
        float colour1Weight = 1 - blend;
        float r = (colour1Weight * color1.r) + (blend * color2.r);
        float g = (colour1Weight * color1.g) + (blend * color2.g);
        float b = (colour1Weight * color1.b) + (blend * color2.b);
        float a = (colour1Weight * color1.a) + (blend * color2.a);

        return new Color(r, g, b, a);
    }

    public static float[] convertListToArray(final List<Color> colors) {
        float[] array = new float[colors.size() * 3];

        int pointer = 0;
        for (Color color : colors) {
            array[pointer++] = color.r;
            array[pointer++] = color.g;
            array[pointer++] = color.b;
        }

        return array;
    }
}
