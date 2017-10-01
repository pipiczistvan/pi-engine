package piengine.core.base.domain;

public abstract class Domain<D extends Dao> {

    public final D dao;

    public Domain(final D dao) {
        this.dao = dao;
    }
}
