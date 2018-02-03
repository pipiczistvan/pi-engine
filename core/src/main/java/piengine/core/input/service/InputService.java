package piengine.core.input.service;

import org.joml.Vector2f;
import piengine.core.base.api.Service;
import piengine.core.base.event.Action;
import piengine.core.base.event.Event;
import piengine.core.base.exception.PIEngineException;
import piengine.core.input.domain.KeyEventType;
import piengine.core.input.interpreter.CursorPosCallback;
import piengine.core.input.interpreter.KeyCallback;
import piengine.core.input.interpreter.MouseButtonCallback;
import piutils.map.ListMap;
import puppeteer.annotation.premade.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InputService implements Service {

    private final KeyCallback keyCallback;
    private final MouseButtonCallback mouseButtonCallback;
    private final CursorPosCallback cursorPosCallback;

    private final ListMap<Integer, Event> releaseEventMap;
    private final ListMap<Integer, Event> pressEventMap;
    private final List<Action<Vector2f>> cursorEvents;

    public InputService() {
        this.releaseEventMap = new ListMap<>();
        this.pressEventMap = new ListMap<>();
        this.cursorEvents = new ArrayList<>();
        this.keyCallback = new KeyCallback(releaseEventMap, pressEventMap);
        this.mouseButtonCallback = new MouseButtonCallback(releaseEventMap, pressEventMap);
        this.cursorPosCallback = new CursorPosCallback(cursorEvents);
    }

    public void addEvent(int key, KeyEventType type, Event event) {
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

    public void addEvent(Action<Vector2f> action) {
        cursorEvents.add(action);
    }

    public KeyCallback getKeyCallback() {
        return keyCallback;
    }

    public MouseButtonCallback getMouseButtonCallback() {
        return mouseButtonCallback;
    }

    public CursorPosCallback getCursorPosCallback() {
        return cursorPosCallback;
    }

    public void clearEvents() {
        pressEventMap.clear();
        releaseEventMap.clear();
        cursorEvents.clear();
    }
}
