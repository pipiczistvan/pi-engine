package piengine.core.input.manager;

import org.joml.Vector2f;
import piengine.core.base.event.Action;
import piengine.core.base.event.Event;
import piengine.core.input.domain.Key;
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

    public void addKeyEvent(final Key key, final KeyEventType type, final Event event) {
        inputService.addKeyEvent(key, type, event);
    }

    public void addCursorEvent(final Action<Vector2f> action) {
        inputService.addCursorEvent(action);
    }

    public void addScrollEvent(final Action<Vector2f> action) {
        inputService.addScrollEvent(action);
    }
}
