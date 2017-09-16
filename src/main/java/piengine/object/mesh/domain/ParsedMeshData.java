package piengine.object.mesh.domain;

public class ParsedMeshData {

    public final float[] vertices;
    public final int[] indices;
    public final float[] textureCoords;

    public ParsedMeshData(final float[] vertices, final int[] indices, final float[] textureCoords) {
        this.vertices = vertices;
        this.indices = indices;
        this.textureCoords = textureCoords;
    }

}
