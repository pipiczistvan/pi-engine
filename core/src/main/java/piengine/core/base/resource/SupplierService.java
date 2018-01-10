package piengine.core.base.resource;

import piengine.core.base.api.Accessor;
import piengine.core.base.api.Interpreter;
import piengine.core.base.api.Service;
import piengine.core.base.api.Terminatable;
import piengine.core.base.domain.Dao;
import piengine.core.base.domain.Domain;
import piengine.core.base.domain.ResourceData;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class SupplierService<K, R extends ResourceData, D extends Dao, M extends Domain<D>> implements Service, Terminatable {

    protected final Interpreter<R, D> interpreter;
    private final Accessor<K, R> accessor;
    private final Map<K, M> resourceMap;

    public SupplierService(final Accessor<K, R> accessor, final Interpreter<R, D> interpreter) {
        this.accessor = accessor;
        this.interpreter = interpreter;

        this.resourceMap = new HashMap<>();
    }

    public M supply(final K key) {
        return resourceMap.computeIfAbsent(key, this::compute);
    }

    @Override
    public void terminate() {
        resourceMap.values().forEach(domain -> interpreter.free(domain.getDao()));
    }

    protected Collection<M> getValues() {
        return resourceMap.values();
    }

    protected abstract M createDomain(final D dao, final R resource);

    private M compute(final K key) {
        R resourceData = accessor.access(key);
        D dao = interpreter.create(resourceData);
        accessor.free(resourceData);
        return createDomain(dao, resourceData);
    }
}
