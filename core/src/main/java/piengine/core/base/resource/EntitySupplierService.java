package piengine.core.base.resource;

import piengine.core.base.api.Accessor;
import piengine.core.base.api.Interpreter;
import piengine.core.base.domain.Dao;
import piengine.core.base.domain.EntityKey;
import piengine.core.base.domain.ResourceData;
import piengine.object.entity.domain.EntityDomain;

public abstract class EntitySupplierService<K extends EntityKey, R extends ResourceData, D extends Dao, M extends EntityDomain<D>> extends SupplierService<K, R, D, M> {

    public EntitySupplierService(final Accessor<K, R> accessor, final Interpreter<R, D> interpreter) {
        super(accessor, interpreter);
    }

    @Override
    public M supply(K key) {
        M domain = super.supply(key);
        key.parent.addChild(domain);

        return domain;
    }
}
