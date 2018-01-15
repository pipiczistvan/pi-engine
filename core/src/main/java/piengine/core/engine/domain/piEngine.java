package piengine.core.engine.domain;

import piengine.core.architecture.scene.domain.Scene;
import piengine.core.base.type.property.ApplicationProperties;
import piengine.core.engine.service.EngineService;
import puppeteer.Puppeteer;

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

    public piEngine(final String applicationProperties, final Collection<String> libraries, final Collection<String> packages) {
        ApplicationProperties.load(ENGINE_PROPERTIES, applicationProperties);

        Collection<String> combinedPackages = new ArrayList<>(PACKAGES);
        combinedPackages.addAll(packages);

        Puppeteer puppeteer = new Puppeteer(libraries, combinedPackages);
        puppeteer.useDefaultAnnotations();
        puppeteer.processAnnotations();

        engineService = puppeteer.getInstanceOf(EngineService.class);
    }

    public void start(Class<? extends Scene> sceneClass) {
        engineService.start(sceneClass);
    }

}
