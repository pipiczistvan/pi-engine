package piengine.core.architecture.service;

import piengine.core.base.api.Service;
import piengine.core.base.api.Terminatable;
import piengine.core.base.domain.Domain;

public abstract class SupplierService<K, D extends Domain> implements Service, Terminatable {

    public abstract D supply(final K key);

}
