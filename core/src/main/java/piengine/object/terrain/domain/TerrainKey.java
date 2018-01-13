package piengine.object.terrain.domain;

import org.joml.Vector3f;
import piengine.core.base.domain.Key;

public class TerrainKey implements Key {

    public final Vector3f position;
    public final Vector3f rotation;
    public final Vector3f scale;
    public final String heightmap;

    public TerrainKey(final Vector3f position, final Vector3f rotation, final Vector3f scale, final String heightmap) {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
        this.heightmap = heightmap;
    }
}
