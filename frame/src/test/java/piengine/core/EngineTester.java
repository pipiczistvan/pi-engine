package piengine.core;

import org.junit.Ignore;
import org.junit.Test;
import piengine.core.domain.InitScene;
import piengine.core.engine.domain.piEngine;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

@Ignore
public class EngineTester {

    private static final String APPLICATION_PROPERTIES = "application";

    @Test
    public void run() {
        piEngine engine = new piEngine(
                APPLICATION_PROPERTIES,
                emptyList(),
                singletonList("^.*/target/.*$"),
                emptyList()
        );
        engine.start(InitScene.class);
    }
}
