package piengine.core.base.domain;

import piengine.object.entity.domain.Entity;

public abstract class EntityKey implements Key {

    public final Entity parent;

    public EntityKey(final Entity parent) {
        this.parent = parent;
    }
}
