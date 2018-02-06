package piengine.io.loader;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractLoader<K, D extends Dto> {

    protected final Map<K, D> loadMap = new HashMap<>();

    public D load(final K key) {
        return loadMap.computeIfAbsent(key, this::createDto);
    }

    protected abstract D createDto(final K key);

}
