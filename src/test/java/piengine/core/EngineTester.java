package piengine.core;

import org.junit.Ignore;
import org.junit.Test;
import piengine.core.engine.domain.piEngine;

@Ignore
public class EngineTester {

    private static final String APPLICATION_PROPERTIES = "src/test/resources/config/application.properties";

    @Test
    public void run() {
        piEngine engine = new piEngine(APPLICATION_PROPERTIES);
        engine.start();
    }

}
