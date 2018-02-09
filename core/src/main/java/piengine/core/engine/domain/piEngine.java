package piengine.core.engine.domain;

import piengine.core.architecture.scene.domain.Scene;
import piengine.core.base.type.property.ApplicationProperties;
import piengine.core.engine.service.EngineService;
import piengine.visual.display.domain.awt.AwtCanvas;
import puppeteer.Puppeteer;

import javax.swing.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

import static java.util.Arrays.asList;

public class piEngine {

    private static final Collection<String> PACKAGES = asList(
            "piengine.*.domain",
            "piengine.*.manager",
            "piengine.*.service",
            "piengine.*.accessor",
            "piengine.*.interpreter",
            "piengine.*.handler"
    );
    private static final String ENGINE_PROPERTIES = "engine";

    private final EngineService engineService;

    public piEngine(final String applicationProperties, final Collection<URL> libraries, final Collection<String> libraryPatterns, final Collection<String> packagePatterns) {
        ApplicationProperties.load(ENGINE_PROPERTIES, applicationProperties);

        Collection<String> combinedPackages = new ArrayList<>(PACKAGES);
        combinedPackages.addAll(packagePatterns);

        Puppeteer puppeteer = new Puppeteer(libraries, libraryPatterns, combinedPackages);
        puppeteer.useDefaultAnnotations();
        puppeteer.processAnnotations();

        engineService = puppeteer.getInstanceOf(EngineService.class);
    }

    public void createGlfwDisplay() {
        engineService.createDisplay();
    }

    public void createAwtDisplay(final JFrame frame, final AwtCanvas canvas) {
        engineService.createDisplay(frame, canvas);
    }

    public void start(Class<? extends Scene> sceneClass) {
        engineService.start(sceneClass);
    }
}
