package piengine.core.base.api;

import piengine.core.base.domain.ResourceData;

public interface Accessor<K, T extends ResourceData> {

    T access(final K key);

    default void free(final T resource) {
    }

}
