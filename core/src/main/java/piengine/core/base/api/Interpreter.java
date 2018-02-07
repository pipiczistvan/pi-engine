package piengine.core.base.api;

import org.apache.log4j.Logger;
import piengine.core.base.domain.Dao;
import piengine.core.base.domain.ResourceData;

public abstract class Interpreter<R extends ResourceData, D extends Dao> {

    private final Logger logger = Logger.getLogger(getClass());

    public D create(final R resource) {
        D dao = createDao(resource);
        if (shouldLog()) {
            logger.info("Dao created: " + getCreateInfo(dao, resource));
        }

        return dao;
    }

    public void free(final D dao) {
        boolean freed = freeDao(dao);
        if (freed) {
            if (shouldLog()) {
                logger.info("Dao freed: " + getFreeInfo(dao));
            }
        }
    }

    protected abstract D createDao(final R resource);

    protected boolean freeDao(final D dao) {
        return false;
    }

    protected String getCreateInfo(final D dao, final R resource) {
        return "";
    }

    protected String getFreeInfo(final D dao) {
        return "";
    }

    protected boolean shouldLog() {
        return true;
    }
}
