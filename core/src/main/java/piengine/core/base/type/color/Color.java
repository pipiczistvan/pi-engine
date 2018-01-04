package piengine.core.base.type.color;

public class Color {

    public final float r;
    public final float g;
    public final float b;
    public final float a;

    public Color(final float r, final float g, final float b, final float a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public Color(final float r, final float g, final float b) {
        this(r, g, b, 1);
    }

    public Color(final float rgba) {
        this(rgba, rgba, rgba, rgba);
    }
}
