package piengine.core.base.api;

import piengine.core.base.domain.Dao;
import piengine.core.base.domain.ResourceData;

public interface Interpreter<R extends ResourceData, D extends Dao> {

    D create(final R r);

    default void free(final D dao) {
    }
}
