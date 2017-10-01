package piengine.core.base.event;

public interface Action<T> {
    void invoke(T t);
}
