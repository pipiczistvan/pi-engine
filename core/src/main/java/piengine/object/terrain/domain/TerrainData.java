package piengine.object.terrain.domain;

import org.joml.Vector3f;
import piengine.core.base.domain.ResourceData;

public class TerrainData implements ResourceData {

    public final Vector3f position;
    public final Vector3f rotation;
    public final Vector3f scale;
    public final float[] vertices;
    public final int[] indices;
    public final float[] colors;
    public final float[] normals;
    public final float[][] heights;

    public TerrainData(final Vector3f position, final Vector3f rotation, final Vector3f scale,
                       final float[] vertices, final int[] indices, final float[] colors,
                       final float[] normals, final float[][] heights) {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
        this.vertices = vertices;
        this.indices = indices;
        this.colors = colors;
        this.normals = normals;
        this.heights = heights;
    }
}
