package piengine.object.terrain.domain;

import piengine.core.base.domain.EntityKey;
import piengine.object.entity.domain.Entity;

public class TerrainKey extends EntityKey {

    public final String heightmap;

    public TerrainKey(final Entity parent, final String heightmap) {
        super(parent);
        this.heightmap = heightmap;
    }
}
