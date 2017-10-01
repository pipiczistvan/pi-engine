package piengine.core.engine.service;

import piengine.core.base.api.Initializable;
import piengine.core.base.api.Renderable;
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
import static piengine.visual.window.domain.WindowEventType.UPDATE;

@Component
public class EngineService {

    private final WindowService windowService;
    private final TimeService timeService;

    private final List<Initializable> initializableServices;
    private final List<Updatable> updatableServices;
    private final List<Renderable> renderableServices;
    private final List<Terminatable> terminatableServices;

    @Wire
    public EngineService(final WindowService windowService,
                         final TimeService timeService,
                         final List<Service> services) {
        this.windowService = windowService;
        this.timeService = timeService;

        this.initializableServices = getServicesOf(services, Initializable.class);
        this.updatableServices = getServicesOf(services, Updatable.class);
        this.renderableServices = getServicesOf(services, Renderable.class);
        this.terminatableServices = getServicesOf(services, Terminatable.class);
    }

    public void start() {
        windowService.addEvent(INITIALIZE, this::initialize);
        windowService.addEvent(UPDATE, this::update);
        windowService.addEvent(CLOSE, this::terminate);
        windowService.createWindow();
    }

    private void initialize() {
        initializableServices.forEach(Initializable::initialize);
    }

    private void update() {
        final double delta = timeService.getDelta();

        updatableServices.forEach(u -> u.update(delta));

        if (timeService.waitTimeSpent()) {
            renderableServices.forEach(Renderable::render);
            windowService.swapBuffers();
        }
    }

    private void terminate() {
        terminatableServices.forEach(Terminatable::terminate);
    }

    @SuppressWarnings("unchecked")
    private <T> List<T> getServicesOf(final List<Service> services, Class<T> type) {
        return services.stream().filter(type::isInstance).map(service -> (T) service).collect(Collectors.toList());
    }

}
