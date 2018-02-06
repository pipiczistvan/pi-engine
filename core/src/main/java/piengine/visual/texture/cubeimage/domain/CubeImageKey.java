package piengine.visual.texture.cubeimage.domain;

import java.util.Objects;

public class CubeImageKey {

    public final String right;
    public final String left;
    public final String top;
    public final String bottom;
    public final String back;
    public final String front;

    public CubeImageKey(final String right, final String left, final String top,
                        final String bottom, final String back, final String front) {
        this.right = right;
        this.left = left;
        this.top = top;
        this.bottom = bottom;
        this.back = back;
        this.front = front;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CubeImageKey that = (CubeImageKey) o;
        return Objects.equals(right, that.right) &&
                Objects.equals(left, that.left) &&
                Objects.equals(top, that.top) &&
                Objects.equals(bottom, that.bottom) &&
                Objects.equals(back, that.back) &&
                Objects.equals(front, that.front);
    }

    @Override
    public int hashCode() {
        return Objects.hash(right, left, top, bottom, back, front);
    }
}
