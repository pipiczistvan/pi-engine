package piengine.core.base.resource;

import piengine.core.base.api.Accessor;
import piengine.core.base.api.Interpreter;
import piengine.core.base.api.Service;
import piengine.core.base.api.Terminatable;
import piengine.core.base.domain.Dao;
import piengine.core.base.domain.Domain;
import piengine.core.base.domain.Key;
import piengine.core.base.domain.ResourceData;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class SupplierService<K extends Key, R extends ResourceData, D extends Dao, M extends Domain<D>> implements Service, Terminatable {

    private final Interpreter<R, D> interpreter;
    private final Accessor<K, R> accessor;
    private final Map<K, R> resourceMap;
    private final Map<K, M> domainMap;

    public SupplierService(final Accessor<K, R> accessor, final Interpreter<R, D> interpreter) {
        this.accessor = accessor;
        this.interpreter = interpreter;

        this.resourceMap = new HashMap<>();
        this.domainMap = new HashMap<>();
    }

    public M supply(final K key) {
        return computeDomain(key);
//        return domainMap.computeIfAbsent(key, this::computeDomain); todo: fix this
    }

    public R load(final K key) {
        return computeResource(key);
//        return resourceMap.computeIfAbsent(key, this::computeResource); todo: fix this
    }

    @Override
    public void terminate() {
        resourceMap.values().forEach(accessor::free);
        domainMap.values().forEach(domain -> interpreter.free(domain.getDao()));
    }

    protected Collection<M> getDomainValues() {
        return domainMap.values();
    }

    protected abstract M createDomain(final D dao, final R resource);

    protected void removeForKey(final K key) {
        resourceMap.remove(key);
        domainMap.remove(key);
    }

    private M computeDomain(final K key) {
        R resourceData = load(key);
        D dao = interpreter.create(resourceData);
        return createDomain(dao, resourceData);
    }

    private R computeResource(final K key) {
        return accessor.access(key);
    }
}
