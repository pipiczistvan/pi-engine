package piengine.core.engine.service;

import piengine.core.architecture.scene.domain.Scene;
import piengine.core.architecture.scene.service.SceneService;
import piengine.core.base.api.Initializable;
import piengine.core.base.api.Renderable;
import piengine.core.base.api.Resizable;
import piengine.core.base.api.Service;
import piengine.core.base.api.Terminatable;
import piengine.core.base.api.Updatable;
import piengine.core.time.service.TimeService;
import piengine.visual.display.service.DisplayService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import java.util.List;
import java.util.stream.Collectors;

import static piengine.visual.display.domain.DisplayEventType.CLOSE;
import static piengine.visual.display.domain.DisplayEventType.INITIALIZE;
import static piengine.visual.display.domain.DisplayEventType.RESIZE;
import static piengine.visual.display.domain.DisplayEventType.UPDATE;

@Component
public class EngineService {

    private final DisplayService displayService;
    private final TimeService timeService;
    private final SceneService sceneService;

    private final List<Initializable> initializableServices;
    private final List<Updatable> updatableServices;
    private final List<Renderable> renderableServices;
    private final List<Terminatable> terminatableServices;
    private final List<Resizable> resizableServices;

    @Wire
    public EngineService(final DisplayService displayService,
                         final TimeService timeService,
                         final SceneService sceneService,
                         final List<Service> services) {
        this.displayService = displayService;
        this.timeService = timeService;
        this.sceneService = sceneService;

        this.initializableServices = getServicesOf(services, Initializable.class);
        this.updatableServices = getServicesOf(services, Updatable.class);
        this.renderableServices = getServicesOf(services, Renderable.class);
        this.terminatableServices = getServicesOf(services, Terminatable.class);
        this.resizableServices = getServicesOf(services, Resizable.class);
    }

    public void start(Class<? extends Scene> sceneClass) {
        sceneService.setDefaultSceneClass(sceneClass);

        displayService.addEvent(INITIALIZE, this::initialize);
        displayService.addEvent(UPDATE, this::update);
        displayService.addEvent(CLOSE, this::terminate);
        displayService.addEvent(RESIZE, this::resize);
        displayService.createDisplay();
    }

    private void initialize() {
        initializableServices.forEach(Initializable::initialize);
    }

    private void update() {
        timeService.update();

//        if (timeService.waitTimeSpent()) {
            float delta = timeService.getDelta();

            updatableServices.forEach(u -> u.update(delta));
            renderableServices.forEach(Renderable::render);
//            displayService.render();

            timeService.frameUpdated();
//        }
    }

    private void terminate() {
        terminatableServices.forEach(Terminatable::terminate);
    }

    private void resize() {
        int width = displayService.getWidth();
        int height = displayService.getHeight();
        int oldWidth = displayService.getOldWidth();
        int oldHeight = displayService.getOldHeight();

        resizableServices.forEach(r -> r.resize(oldWidth, oldHeight, width, height));
    }

    @SuppressWarnings("unchecked")
    private <T> List<T> getServicesOf(final List<Service> services, Class<T> type) {
        return services.stream().filter(type::isInstance).map(service -> (T) service).collect(Collectors.toList());
    }

}
