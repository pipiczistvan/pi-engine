package piengine.core.base.type.color;

public class Color {

    public float r;
    public float g;
    public float b;
    public float a;

    public Color(final float r, final float g, final float b, final float a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public Color(final float r, final float g, final float b) {
        this(r, g, b, 1);
    }

    public Color(final float rgb) {
        this(rgb, rgb, rgb);
    }

    public Color(final Color color) {
        this(color.r, color.g, color.b, color.a);
    }

    public void set(final float r, final float g, final float b, final float a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public void set(final float r, final float g, final float b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public void set(final Color color) {
        this.r = color.r;
        this.g = color.g;
        this.b = color.b;
        this.a = color.a;
    }
}
