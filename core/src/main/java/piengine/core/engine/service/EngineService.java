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
import piengine.visual.window.service.WindowService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import java.util.List;
import java.util.stream.Collectors;

import static piengine.visual.window.domain.WindowEventType.CLOSE;
import static piengine.visual.window.domain.WindowEventType.INITIALIZE;
import static piengine.visual.window.domain.WindowEventType.RESIZE;
import static piengine.visual.window.domain.WindowEventType.UPDATE;

@Component
public class EngineService {

    private final WindowService windowService;
    private final TimeService timeService;
    private final SceneService sceneService;

    private final List<Initializable> initializableServices;
    private final List<Updatable> updatableServices;
    private final List<Renderable> renderableServices;
    private final List<Terminatable> terminatableServices;
    private final List<Resizable> resizableServices;

    @Wire
    public EngineService(final WindowService windowService,
                         final TimeService timeService,
                         final SceneService sceneService,
                         final List<Service> services) {
        this.windowService = windowService;
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

        windowService.addEvent(INITIALIZE, this::initialize);
        windowService.addEvent(UPDATE, this::update);
        windowService.addEvent(CLOSE, this::terminate);
        windowService.addEvent(RESIZE, this::resize);
        windowService.createWindow();
    }

    private void initialize() {
        initializableServices.forEach(Initializable::initialize);
    }

    private void update() {
        timeService.update();

        if (timeService.waitTimeSpent()) {
            float delta = timeService.getDelta();

            updatableServices.forEach(u -> u.update(delta));
            renderableServices.forEach(Renderable::render);
            windowService.swapBuffers();

            timeService.frameUpdated();
        }
    }

    private void terminate() {
        terminatableServices.forEach(Terminatable::terminate);
    }

    private void resize() {
        int width = windowService.getWidth();
        int height = windowService.getHeight();
        int oldWidth = windowService.getOldWidth();
        int oldHeight = windowService.getOldHeight();

        resizableServices.forEach(r -> r.resize(oldWidth, oldHeight, width, height));
    }

    @SuppressWarnings("unchecked")
    private <T> List<T> getServicesOf(final List<Service> services, Class<T> type) {
        return services.stream().filter(type::isInstance).map(service -> (T) service).collect(Collectors.toList());
    }

}
