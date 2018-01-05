package piengine.object.water.domain;

import piengine.object.entity.domain.Entity;
import piengine.object.entity.domain.EntityDomain;

public class Water extends EntityDomain<WaterDao> {

    public Water(final Entity parent, final WaterDao dao) {
        super(parent, dao);
    }
}
