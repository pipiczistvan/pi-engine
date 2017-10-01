package piengine.core.base.resource;

import piengine.core.base.api.Accessor;
import piengine.core.base.api.Interpreter;
import piengine.core.base.api.Service;
import piengine.core.base.api.Terminatable;
import piengine.core.base.domain.Dao;
import piengine.core.base.domain.Domain;
import piengine.core.base.domain.ResourceData;

import java.util.HashMap;
import java.util.Map;

public abstract class SupplierService<M extends Domain<D>, D extends Dao, R extends ResourceData> implements Service, Terminatable {

    protected final Interpreter<D, R> interpreter;
    private final Accessor<R> accessor;
    private final Map<String, M> resourceMap;

    public SupplierService(final Accessor<R> accessor, final Interpreter<D, R> interpreter) {
        this.accessor = accessor;
        this.interpreter = interpreter;

        this.resourceMap = new HashMap<>();
    }

    public M supply(final String file) {
        return resourceMap.computeIfAbsent(file, this::compute);
    }

    @Override
    public void terminate() {
        resourceMap.values().forEach(domain -> interpreter.free(domain.dao));
    }

    protected abstract M createDomain(final D dao, final R resource);

    private M compute(final String file) {
        R resourceData = accessor.access(file);
        D dao = interpreter.create(resourceData);
        accessor.free(resourceData);
        return createDomain(dao, resourceData);
    }

}
