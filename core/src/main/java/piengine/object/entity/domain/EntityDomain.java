package piengine.object.entity.domain;

import piengine.core.base.domain.Dao;
import piengine.core.base.domain.Domain;

public abstract class EntityDomain<D extends Dao> extends Entity implements Domain<D> {

    private final D dao;

    public EntityDomain(final Entity parent, final D dao) {
        super(parent);
        this.dao = dao;
    }

    @Override
    public D getDao() {
        return dao;
    }
}
