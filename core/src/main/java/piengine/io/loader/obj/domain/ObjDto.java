package piengine.io.loader.obj.domain;

import piengine.io.loader.Dto;

public class ObjDto implements Dto {

    public final float[] vertices;
    public final int[] indices;
    public final float[] textureCoords;
    public final float[] normals;

    public ObjDto(final float[] vertices, final int[] indices, final float[] textureCoords, final float[] normals) {
        this.vertices = vertices;
        this.indices = indices;
        this.textureCoords = textureCoords;
        this.normals = normals;
    }
}
