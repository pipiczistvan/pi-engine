package piengine.core.base.domain;

public interface Domain<D extends Dao> {
    D getDao();
}
