package piengine.core.base.api;

import piengine.core.base.domain.ResourceData;

public interface Accessor<T extends ResourceData> {

    T access(final String file);

    default void free(final T resource) {
    }

}
