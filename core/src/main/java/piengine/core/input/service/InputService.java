package piengine.core.input.service;

import org.joml.Vector2f;
import piengine.core.base.api.Service;
import piengine.core.base.event.Action;
import piengine.core.base.event.Event;
import piengine.core.input.domain.Key;
import piengine.core.input.domain.KeyEventType;
import piengine.visual.display.service.DisplayService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

@Component
public class InputService implements Service {

    private final DisplayService displayService;

    @Wire
    public InputService(final DisplayService displayService) {
        this.displayService = displayService;
    }

    public void addKeyEvent(final Key key, final KeyEventType type, final Event event) {
        displayService.addKeyEvent(key, type, event);
    }

    public void addCursorEvent(final Action<Vector2f> action) {
        displayService.addCursorEvent(action);
    }

    public void addScrollEvent(final Action<Vector2f> action) {
        displayService.addScrollEvent(action);
    }
}
