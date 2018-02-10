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
import static piengine.core.base.type.property.ApplicationProperties.get;
import static piengine.core.base.type.property.PropertyKeys.WINDOW_HEIGHT;
import static piengine.core.base.type.property.PropertyKeys.WINDOW_MIN_HEIGHT;
import static piengine.core.base.type.property.PropertyKeys.WINDOW_MIN_WIDTH;
import static piengine.core.base.type.property.PropertyKeys.WINDOW_TITLE;
import static piengine.core.base.type.property.PropertyKeys.WINDOW_WIDTH;

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
        frame.setTitle(get(WINDOW_TITLE));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setPreferredSize(new Dimension(get(WINDOW_WIDTH), get(WINDOW_HEIGHT)));
        frame.setMinimumSize(new Dimension(get(WINDOW_MIN_WIDTH), get(WINDOW_MIN_HEIGHT)));

        AwtCanvas canvas = new AwtCanvas();
        frame.add(canvas, BorderLayout.CENTER);

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
