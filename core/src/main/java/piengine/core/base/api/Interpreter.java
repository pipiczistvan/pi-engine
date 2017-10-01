package piengine.core.base.api;

import piengine.core.base.domain.Dao;
import piengine.core.base.domain.ResourceData;

public interface Interpreter<D extends Dao, R extends ResourceData> {

    D create(final R r);

    default void free(final D dao) {
    }

}
