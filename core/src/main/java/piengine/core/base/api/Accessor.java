package piengine.core.base.api;

import org.apache.log4j.Logger;
import piengine.core.base.domain.ResourceData;

public abstract class Accessor<K, R extends ResourceData> {

    private final Logger logger = Logger.getLogger(getClass());

    public R access(final K key) {
        R resource = accessResource(key);
        logger.info("Resource accessed: " + getAccessInfo(key, resource));

        return resource;
    }

    public void free(final R resource) {
        boolean freed = freeResource(resource);
        if (freed) {
            logger.info("Resource freed");
        }
    }

    protected abstract R accessResource(final K key);

    protected boolean freeResource(final R resource) {
        return false;
    }

    protected abstract String getAccessInfo(final K key, final R resource);
}
