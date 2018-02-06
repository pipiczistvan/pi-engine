package piengine.io.loader.terrain.domain;

import piengine.io.loader.Dto;

public class TerrainDto implements Dto {

    public final float[] vertices;
    public final int[] indices;
    public final float[] colors;
    public final float[] normals;
    public final float[][] heights;

    public TerrainDto(final float[] vertices, final int[] indices, final float[] colors,
                      final float[] normals, final float[][] heights) {
        this.vertices = vertices;
        this.indices = indices;
        this.colors = colors;
        this.normals = normals;
        this.heights = heights;
    }
}
