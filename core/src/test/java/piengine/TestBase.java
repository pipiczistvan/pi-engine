package piengine;

import org.junit.Before;
import piengine.core.property.domain.ApplicationProperties;

public abstract class TestBase {

    private static final String ENGINE_PROPERTIES = "src/main/resources/config/engine.properties";
    private static final String APPLICATION_PROPERTIES = "src/test/resources/config/application.properties";

    @Before
    public void setUp() throws Exception {
        ApplicationProperties.load(ENGINE_PROPERTIES, APPLICATION_PROPERTIES);
    }

}
