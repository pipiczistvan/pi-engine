package piengine.visual.display.domain;

import org.joml.Vector2f;
import org.joml.Vector2i;
import piengine.core.base.event.Action;
import piengine.core.base.event.Event;
import piengine.core.base.exception.PIEngineException;
import piengine.core.input.domain.Key;
import piengine.core.input.domain.KeyEventType;
import piutils.map.ListMap;

import java.util.ArrayList;
import java.util.List;

public abstract class Display {

    protected final ListMap<Integer, Event> releaseEventMap = new ListMap<>();
    protected final ListMap<Integer, Event> pressEventMap = new ListMap<>();
    protected final List<Action<Vector2f>> cursorEvents = new ArrayList<>();
    protected final List<Action<Vector2f>> scrollEvents = new ArrayList<>();
    protected final ListMap<DisplayEventType, Event> eventMap = new ListMap<>();

    public void addEvent(final DisplayEventType type, final Event event) {
        eventMap.put(type, event);
    }

    public void addKeyEvent(final Key key, final KeyEventType type, final Event event) {
        switch (type) {
            case PRESS:
                pressEventMap.put(keyToCode(key), event);
                break;
            case RELEASE:
                releaseEventMap.put(keyToCode(key), event);
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

    protected abstract int keyToCode(final Key key);

    // Interventions

    public abstract void startLoop();

    public abstract void closeDisplay();

    public abstract void setCursorVisibility(final boolean visible);

    public abstract Vector2f getPointer();

    public abstract void setPointer(final Vector2f position);

    // Window and viewport

    public abstract Vector2i getWindowSize();

    public abstract Vector2i getOldWindowSize();

    public abstract Vector2i getViewport();

    public abstract Vector2i getOldViewport();

    public abstract Vector2f getViewportCenter();
}
