package piengine.object.terrain.domain;

import piengine.core.base.domain.ResourceData;
import piengine.object.entity.domain.Entity;

public class TerrainData implements ResourceData {

    public final Entity parent;
    public final float[] vertices;
    public final int[] indices;
    public final float[][] heights;
    public final float size;

    public TerrainData(final Entity parent, final float[] vertices, final int[] indices, final float[][] heights, final float size) {
        this.parent = parent;
        this.vertices = vertices;
        this.indices = indices;
        this.heights = heights;
        this.size = size;
    }
}
