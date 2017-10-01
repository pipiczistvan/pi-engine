package piengine.core.engine.domain;

import piengine.core.engine.service.EngineService;
import piengine.core.property.domain.ApplicationProperties;
import puppeteer.Puppeteer;
import puppeteer.annotation.premade.Wire;

public class piEngine {

    private static final String PREFIX = "piengine";
    private static final String[] PACKAGES = {
            "piengine.*.domain",
            "piengine.*.manager",
            "piengine.*.service",
            "piengine.*.accessor",
            "piengine.*.interpreter"
    };
    private static final String ENGINE_PROPERTIES = "engine";

    @Wire
    private static EngineService engineService;

    public piEngine(final String applicationProperties) {
        ApplicationProperties.load(ENGINE_PROPERTIES, applicationProperties);

        Puppeteer puppeteer = new Puppeteer(PREFIX, PACKAGES);
        puppeteer.useDefaultAnnotations();
        puppeteer.processAnnotations();
    }

    public void start() {
        engineService.start();
    }

}
