package piengine.core;

import org.junit.Ignore;
import org.junit.Test;
import piengine.core.domain.InitScene;
import piengine.core.engine.domain.piEngine;
import piengine.visual.display.domain.awt.AwtCanvas;

import javax.swing.*;
import java.awt.*;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

@Ignore
public class EngineTester {

    private static final String APPLICATION_PROPERTIES = "application";

    @Test
    public void testGlfw() {
        piEngine engine = createEngine();

        engine.createGlfwDisplay();
        engine.start(InitScene.class);
    }

    @Test
    public void testAwt() {
        piEngine engine = createEngine();

        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());

        AwtCanvas canvas = new AwtCanvas();
        frame.getContentPane().add(canvas, BorderLayout.CENTER);

        engine.createAwtDisplay(frame, canvas);
        engine.start(InitScene.class);
    }

    private piEngine createEngine() {
        return new piEngine(
                APPLICATION_PROPERTIES,
                emptyList(),
                singletonList("^.*/target/.*$"),
                emptyList()
        );
    }
}
