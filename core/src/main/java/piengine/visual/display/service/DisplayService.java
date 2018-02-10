package piengine.visual.display.service;

import org.joml.Vector2f;
import org.joml.Vector2i;
import piengine.core.base.event.Action;
import piengine.core.base.event.Event;
import piengine.visual.display.domain.DisplayEventType;
import piengine.visual.display.domain.awt.AwtCanvas;
import piengine.visual.display.interpreter.DisplayInterpreter;
import piutils.map.ListMap;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import javax.swing.*;
import java.util.List;

@Component
public class DisplayService {

    private final DisplayInterpreter displayInterpreter;

    @Wire
    public DisplayService(final DisplayInterpreter displayInterpreter) {
        this.displayInterpreter = displayInterpreter;
    }

    public void addEvent(final DisplayEventType type, final Event event) {
        displayInterpreter.addEvent(type, event);
    }

    // Interventions

    public void createDisplay() {
        displayInterpreter.createDisplay();
    }

    public void createDisplay(final JFrame frame, final AwtCanvas canvas) {
        displayInterpreter.createDisplay(frame, canvas);
    }

    public void startLoop() {
        displayInterpreter.startLoop();
    }

    public void closeDisplay() {
        displayInterpreter.closeDisplay();
    }

    public void setCursorVisibility(final boolean visible) {
        displayInterpreter.setCursorVisibility(visible);
    }

    public void setPointer(final Vector2f position) {
        displayInterpreter.setPointer(position);
    }

    // Window and viewport

    public Vector2i getWindowSize() {
        return displayInterpreter.getWindowSize();
    }

    public Vector2i getOldWindowSize() {
        return displayInterpreter.getOldWindowSize();
    }

    public Vector2i getViewport() {
        return displayInterpreter.getViewport();
    }

    public Vector2i getOldViewport() {
        return displayInterpreter.getOldViewport();
    }

    public Vector2f getViewportCenter() {
        return displayInterpreter.getViewportCenter();
    }

    // Events

    public ListMap<Integer, Event> getReleaseEventMap() {
        return displayInterpreter.getReleaseEventMap();
    }

    public ListMap<Integer, Event> getPressEventMap() {
        return displayInterpreter.getPressEventMap();
    }

    public List<Action<Vector2f>> getCursorEvents() {
        return displayInterpreter.getCursorEvents();
    }

    public List<Action<Vector2f>> getScrollEvents() {
        return displayInterpreter.getScrollEvents();
    }
}
