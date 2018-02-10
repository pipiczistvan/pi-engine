package piengine.core.input.service;

import org.joml.Vector2f;
import piengine.core.base.api.Service;
import piengine.core.base.event.Action;
import piengine.core.base.event.Event;
import piengine.core.base.exception.PIEngineException;
import piengine.core.input.domain.KeyEventType;
import piengine.visual.display.service.DisplayService;
import piutils.map.ListMap;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import java.util.List;

@Component
public class InputService implements Service {

    private final ListMap<Integer, Event> releaseEventMap;
    private final ListMap<Integer, Event> pressEventMap;
    private final List<Action<Vector2f>> cursorEvents;
    private final List<Action<Vector2f>> scrollEvents;

    @Wire
    public InputService(final DisplayService displayService) {
        this.releaseEventMap = displayService.getReleaseEventMap();
        this.pressEventMap = displayService.getPressEventMap();
        this.cursorEvents = displayService.getCursorEvents();
        this.scrollEvents = displayService.getScrollEvents();
    }

    public void addKeyEvent(final int key, final KeyEventType type, final Event event) {
        switch (type) {
            case PRESS:
                pressEventMap.put(key, event);
                break;
            case RELEASE:
                releaseEventMap.put(key, event);
                break;
            default:
                throw new PIEngineException("Invalid key event domain!");
        }
    }

    public void addCursorEvent(final Action<Vector2f> action) {
        cursorEvents.add(action);
    }

    public void addScrollEvent(final Action<Vector2f> action) {
        scrollEvents.add(action);
    }

    public void clearEvents() {
        pressEventMap.clear();
        releaseEventMap.clear();
        cursorEvents.clear();
        scrollEvents.clear();
    }
}
