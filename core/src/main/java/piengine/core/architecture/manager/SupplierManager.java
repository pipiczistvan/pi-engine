package piengine.core.architecture.manager;

import piengine.core.architecture.service.SupplierService;
import piengine.core.base.api.Service;
import piengine.core.base.domain.Domain;

//todo: Unique ide ?
//todo: terminate on scene setting
public abstract class SupplierManager<K, D extends Domain> implements Service {

    protected final SupplierService<K, D> supplierService;

    public SupplierManager(final SupplierService<K, D> supplierService) {
        this.supplierService = supplierService;
    }

    public D supply(final K key) {
        return supplierService.supply(key);
    }
}
