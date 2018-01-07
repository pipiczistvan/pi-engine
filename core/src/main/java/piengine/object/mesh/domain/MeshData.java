package piengine.object.mesh.domain;

import piengine.core.base.domain.ResourceData;

public class MeshData implements ResourceData {

    public final float[] vertices;
    public final int[] indices;
    public final float[] textureCoords;
    public final float[] normals;

    public MeshData(final float[] vertices, final int[] indices, final float[] textureCoords, final float[] normals) {
        this.vertices = vertices;
        this.indices = indices;
        this.textureCoords = textureCoords;
        this.normals = normals;
    }

}
