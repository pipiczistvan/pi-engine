package piengine.visual.display.domain;

import org.joml.Vector2f;
import org.joml.Vector2i;
import piengine.core.base.event.Action;
import piengine.core.base.event.Event;
import piutils.map.ListMap;

import java.util.List;

public abstract class Display {

    public final ListMap<Integer, Event> releaseEventMap;
    public final ListMap<Integer, Event> pressEventMap;
    public final List<Action<Vector2f>> cursorEvents;
    public final List<Action<Vector2f>> scrollEvents;

    public Display(final ListMap<Integer, Event> releaseEventMap, final ListMap<Integer, Event> pressEventMap, final List<Action<Vector2f>> cursorEvents, final List<Action<Vector2f>> scrollEvents) {
        this.releaseEventMap = releaseEventMap;
        this.pressEventMap = pressEventMap;
        this.cursorEvents = cursorEvents;
        this.scrollEvents = scrollEvents;
    }

    protected final ListMap<DisplayEventType, Event> eventMap = new ListMap<>();

    public void addEvent(final DisplayEventType type, final Event event) {
        eventMap.put(type, event);
    }

    // Interventions

    public abstract void startLoop();

    public abstract void closeDisplay();

    public abstract void setCursorVisibility(final boolean visible);

    public abstract void setPointer(final Vector2f position);

    // Window and viewport

    public abstract Vector2i getWindowSize();

    public abstract Vector2i getOldWindowSize();

    public abstract Vector2i getViewport();

    public abstract Vector2i getOldViewport();

    public abstract Vector2f getViewportCenter();
}
