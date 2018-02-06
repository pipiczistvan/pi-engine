package piengine.io.loader.terrain.domain;

public class TerrainGrid {

    public final float[] positions;
    public final float[] colors;
    public final float[] normals;
    public final int[] indices;

    public TerrainGrid(final float[] positions, final float[] colors, final float[] normals, final int[] indices) {
        this.positions = positions;
        this.colors = colors;
        this.normals = normals;
        this.indices = indices;
    }
}
