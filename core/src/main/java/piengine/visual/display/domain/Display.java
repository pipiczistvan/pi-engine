package piengine.visual.display.domain;

import org.joml.Vector2f;
import org.joml.Vector2i;
import piengine.core.base.event.Event;
import piutils.map.ListMap;

public abstract class Display {

    protected final ListMap<DisplayEventType, Event> eventMap = new ListMap<>();

    public void addEvent(final DisplayEventType type, final Event event) {
        eventMap.put(type, event);
    }

    // Interventions

    public abstract void startLoop();

    public abstract void closeDisplay();

    public abstract void setCursorVisibility(final boolean visible);

    // Pointer

    public abstract Vector2f getPointer();

    public abstract void setPointer(final Vector2f position);

    // Window and viewport

    public abstract Vector2i getWindowSize();

    public abstract Vector2i getOldWindowSize();

    public abstract Vector2i getViewport();

    public abstract Vector2i getOldViewport();

    public abstract Vector2f getViewportCenter();
}
