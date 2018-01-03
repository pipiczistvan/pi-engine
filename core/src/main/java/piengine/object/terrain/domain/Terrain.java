package piengine.object.terrain.domain;

import piengine.object.entity.domain.EntityDomain;

public class Terrain extends EntityDomain<TerrainDao> {

    private final float[][] heights;

    //todo: parent nem kell? (asset miatt) -> supplyservice-nek ne csak string mehessen be
    public Terrain(final TerrainDao dao, final float[][] heights) {
        super(dao);

        this.heights = heights;
    }



}
