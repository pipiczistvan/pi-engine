package piengine.object.terrain.domain;

import piengine.object.entity.domain.EntityDomain;

public class Terrain extends EntityDomain<TerrainDao> {

    //todo: parent nem kell? (asset miatt) -> supplyservice-nek ne csak string mehessen be
    public Terrain(final TerrainDao dao) {
        super(dao);
    }
}
