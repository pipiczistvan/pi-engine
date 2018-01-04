package piengine;

import org.junit.Before;
import piengine.core.base.type.property.ApplicationProperties;

public abstract class TestBase {

    private static final String ENGINE_PROPERTIES = "engine";
    private static final String APPLICATION_PROPERTIES = "application";

    @Before
    public void setUp() throws Exception {
        ApplicationProperties.load(ENGINE_PROPERTIES, APPLICATION_PROPERTIES);
    }

}
