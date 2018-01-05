package piengine.object.terrain.domain;

import piengine.core.base.domain.ResourceData;
import piengine.object.entity.domain.Entity;

public class TerrainData implements ResourceData {

    public final Entity parent;
    public final float[] vertices;
    public final int[] indices;
    public final float[] colors;
    public final float[] normals;
    public final float[][] heights;

    public TerrainData(final Entity parent, final float[] vertices, final int[] indices,
                       final float[] colors, final float[] normals, final float[][] heights) {
        this.parent = parent;
        this.vertices = vertices;
        this.indices = indices;
        this.colors = colors;
        this.normals = normals;
        this.heights = heights;
    }
}
