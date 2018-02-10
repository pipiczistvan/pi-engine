package piengine.visual.display.service;

import org.joml.Vector2f;
import org.joml.Vector2i;
import piengine.core.base.event.Action;
import piengine.core.base.event.Event;
import piengine.core.input.domain.Key;
import piengine.core.input.domain.KeyEventType;
import piengine.visual.display.domain.DisplayEventType;
import piengine.visual.display.domain.awt.AwtCanvas;
import piengine.visual.display.interpreter.DisplayInterpreter;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import javax.swing.*;

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

    public void addKeyEvent(final Key key, final KeyEventType type, final Event event) {
        displayInterpreter.addKeyEvent(key, type, event);
    }

    public void addCursorEvent(final Action<Vector2f> action) {
        displayInterpreter.addCursorEvent(action);
    }

    public void addScrollEvent(final Action<Vector2f> action) {
        displayInterpreter.addScrollEvent(action);
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

    public Vector2f getPointer() {
        return displayInterpreter.getPointer();
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
}
