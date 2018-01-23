package piengine.object.animatedmodel.domain;

public class GeometryData {

    public final float[] vertices;
    public final float[] textureCoords;
    public final float[] normals;
    public final int[] indices;
    public final int[] jointIds;
    public final float[] vertexWeights;

    public GeometryData(final float[] vertices, final float[] textureCoords, final float[] normals,
                        final int[] indices, final int[] jointIds, final float[] vertexWeights) {
        this.vertices = vertices;
        this.textureCoords = textureCoords;
        this.normals = normals;
        this.indices = indices;
        this.jointIds = jointIds;
        this.vertexWeights = vertexWeights;
    }
}
