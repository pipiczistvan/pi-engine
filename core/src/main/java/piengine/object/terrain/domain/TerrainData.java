package piengine.object.terrain.domain;

import piengine.object.mesh.domain.MeshData;

public class TerrainData extends MeshData {

    public final float[][] heights;
    public final float size;

    public TerrainData(final float[] vertices, final int[] indices, final float[] textureCoords, final float[][] heights, final float size) {
        super(vertices, indices, textureCoords);

        this.heights = heights;
        this.size = size;
    }
}
