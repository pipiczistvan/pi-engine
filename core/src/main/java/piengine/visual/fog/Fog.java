package piengine.visual.fog;

import piengine.core.base.type.color.Color;

public class Fog {

    public final Color color;
    public final float density;
    public final float gradient;

    public Fog(final Color color, final float density, final float gradient) {
        this.color = color;
        this.density = density;
        this.gradient = gradient;
    }
}
