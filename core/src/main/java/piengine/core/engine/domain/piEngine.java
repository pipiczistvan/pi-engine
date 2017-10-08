package piengine.core.engine.domain;

import piengine.core.engine.service.EngineService;
import piengine.core.property.domain.ApplicationProperties;
import puppeteer.Puppeteer;
import puppeteer.annotation.premade.Wire;

import java.util.ArrayList;
import java.util.Collection;

import static java.util.Arrays.asList;

public class piEngine {

    private static final Collection<String> PACKAGES = asList(
            "piengine.*.domain",
            "piengine.*.manager",
            "piengine.*.service",
            "piengine.*.accessor",
            "piengine.*.interpreter"
    );
    private static final String ENGINE_PROPERTIES = "engine";

    @Wire
    private static EngineService engineService;

    public piEngine(final String applicationProperties, final Collection<String> libraries, final Collection<String> packages) {
        ApplicationProperties.load(ENGINE_PROPERTIES, applicationProperties);

        Collection<String> combinedPackages = new ArrayList<>(PACKAGES);
        combinedPackages.addAll(packages);

        Puppeteer puppeteer = new Puppeteer(libraries, combinedPackages);
        puppeteer.useDefaultAnnotations();
        puppeteer.processAnnotations();
    }

    public void start() {
        engineService.start();
    }

}
