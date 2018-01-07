package piengine.object.mesh.domain;

public class ParsedMeshData {

    public final float[] vertices;
    public final int[] indices;
    public final float[] textureCoords;
    public final float[] normals;

    public ParsedMeshData(final float[] vertices, final int[] indices, final float[] textureCoords, final float[] normals) {
        this.vertices = vertices;
        this.indices = indices;
        this.textureCoords = textureCoords;
        this.normals = normals;
    }
}
