package piengine.visual.display.interpreter;

import org.joml.Vector2f;
import org.joml.Vector2i;
import piengine.core.base.event.Action;
import piengine.core.base.event.Event;
import piengine.core.time.service.TimeService;
import piengine.visual.display.domain.Display;
import piengine.visual.display.domain.DisplayEventType;
import piengine.visual.display.domain.awt.AwtCanvas;
import piengine.visual.display.domain.awt.AwtFrame;
import piengine.visual.display.domain.glfw.GlfwWindow;
import piutils.map.ListMap;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class DisplayInterpreter {

    private final TimeService timeService;
    private final ListMap<Integer, Event> releaseEventMap = new ListMap<>();
    private final ListMap<Integer, Event> pressEventMap = new ListMap<>();
    private final List<Action<Vector2f>> cursorEvents = new ArrayList<>();
    private final List<Action<Vector2f>> scrollEvents = new ArrayList<>();

    private Display display;

    @Wire
    public DisplayInterpreter(final TimeService timeService) {
        this.timeService = timeService;
    }

    public void addEvent(final DisplayEventType type, final Event event) {
        display.addEvent(type, event);
    }

    // Interventions

    public void createDisplay() {
        this.display = new GlfwWindow(timeService, releaseEventMap, pressEventMap, cursorEvents, scrollEvents);
    }

    public void createDisplay(final JFrame frame, final AwtCanvas canvas) {
        this.display = new AwtFrame(timeService, frame, canvas, releaseEventMap, pressEventMap, cursorEvents, scrollEvents);
    }

    public void startLoop() {
        display.startLoop();
    }

    public void closeDisplay() {
        display.closeDisplay();
    }

    public void setCursorVisibility(final boolean visible) {
        display.setCursorVisibility(visible);
    }

    public void setPointer(final Vector2f position) {
        display.setPointer(position);
    }

    // Window and viewport

    public Vector2i getWindowSize() {
        return display.getWindowSize();
    }

    public Vector2i getOldWindowSize() {
        return display.getOldWindowSize();
    }

    public Vector2i getViewport() {
        return display.getViewport();
    }

    public Vector2i getOldViewport() {
        return display.getOldViewport();
    }

    public Vector2f getViewportCenter() {
        return display.getViewportCenter();
    }

    // Events

    public ListMap<Integer, Event> getReleaseEventMap() {
        return releaseEventMap;
    }

    public ListMap<Integer, Event> getPressEventMap() {
        return pressEventMap;
    }

    public List<Action<Vector2f>> getCursorEvents() {
        return cursorEvents;
    }

    public List<Action<Vector2f>> getScrollEvents() {
        return scrollEvents;
    }
}
