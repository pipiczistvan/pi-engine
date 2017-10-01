package piengine.object.mesh.domain;

import piengine.core.base.domain.ResourceData;

public class MeshData implements ResourceData {

    public final float[] vertices;
    public final int[] indices;
    public final float[] textureCoords;

    public MeshData(float[] vertices, int[] indices, float[] textureCoords) {
        this.vertices = vertices;
        this.indices = indices;
        this.textureCoords = textureCoords;
    }

}
