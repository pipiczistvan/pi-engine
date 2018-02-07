package piengine.core.base.resource;

import piengine.core.base.api.Accessor;
import piengine.core.base.api.Interpreter;
import piengine.core.base.api.Service;
import piengine.core.base.api.Terminatable;
import piengine.core.base.domain.Dao;
import piengine.core.base.domain.Domain;
import piengine.core.base.domain.Key;
import piengine.core.base.domain.ResourceData;

import java.util.HashMap;
import java.util.Map;

public abstract class SupplierService<K extends Key, R extends ResourceData, D extends Dao, M extends Domain<D>> implements Service, Terminatable {

    private final Interpreter<R, D> interpreter;
    private final Accessor<K, R> accessor;
    private final Map<K, R> resourceMap;
    protected final Map<K, M> domainMap;

    public SupplierService(final Accessor<K, R> accessor, final Interpreter<R, D> interpreter) {
        this.accessor = accessor;
        this.interpreter = interpreter;

        this.resourceMap = new HashMap<>();
        this.domainMap = new HashMap<>();
    }

    public M supply(final K key) {
        return domainMap.computeIfAbsent(key, this::computeDomain);
    }

    public R load(final K key) {
        return resourceMap.computeIfAbsent(key, this::computeResource);
    }

    @Override
    public void terminate() {
        clearDomains();
        clearResources();
    }

    private void clearDomains() {
        domainMap.values().forEach(domain -> interpreter.free(domain.getDao()));
        domainMap.clear();
    }

    private void clearResources() {
        resourceMap.values().forEach(accessor::free);
        resourceMap.clear();
    }

    protected abstract M createDomain(final D dao, final R resource);

    protected M computeDomain(final K key) {
        R resourceData = load(key);
        D dao = interpreter.create(resourceData);
        return createDomain(dao, resourceData);
    }

    private R computeResource(final K key) {
        return accessor.access(key);
    }
}
