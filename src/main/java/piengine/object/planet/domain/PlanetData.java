package piengine.object.planet.domain;

import piengine.core.base.domain.ResourceData;

public class PlanetData implements ResourceData {

    public final float[] vertices;
    public final int[] indices;

    public PlanetData(final float[] vertices, final int[] indices) {
        this.vertices = vertices;
        this.indices = indices;
    }

}
