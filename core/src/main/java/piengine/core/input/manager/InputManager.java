package piengine.core.input.manager;

import org.joml.Vector2f;
import piengine.core.base.event.Action;
import piengine.core.base.event.Event;
import piengine.core.input.domain.KeyEventType;
import piengine.core.input.service.InputService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

@Component
public class InputManager {

    private final InputService inputService;

    @Wire
    public InputManager(final InputService inputService) {
        this.inputService = inputService;
    }

    public void addEvent(int key, KeyEventType type, Event event) {
        inputService.addEvent(key, type, event);
    }

    public void addEvent(Action<Vector2f> action) {
        inputService.addEvent(action);
    }

    public void clearEvents() {
        inputService.clearEvents();
    }
}
