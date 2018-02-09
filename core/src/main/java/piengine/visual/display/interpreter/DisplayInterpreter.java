package piengine.visual.display.interpreter;

import org.joml.Vector2f;
import org.joml.Vector2i;
import piengine.core.base.event.Event;
import piengine.core.input.service.InputService;
import piengine.core.time.service.TimeService;
import piengine.visual.display.domain.Display;
import piengine.visual.display.domain.DisplayEventType;
import piengine.visual.display.domain.awt.AwtCanvas;
import piengine.visual.display.domain.awt.AwtFrame;
import piengine.visual.display.domain.glfw.GlfwWindow;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import javax.swing.*;

@Component
public class DisplayInterpreter {

    private final TimeService timeService;
    private final InputService inputService;

    private Display display;

    @Wire
    public DisplayInterpreter(final TimeService timeService, final InputService inputService) {
        this.timeService = timeService;
        this.inputService = inputService;
    }

    public void addEvent(final DisplayEventType type, final Event event) {
        display.addEvent(type, event);
    }

    // Interventions

    public void createDisplay() {
        this.display = new GlfwWindow(timeService, inputService);
    }

    public void createDisplay(final JFrame frame, final AwtCanvas canvas) {
        this.display = new AwtFrame(timeService, frame, canvas);
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

    // Pointer

    public Vector2f getPointer() {
        return display.getPointer();
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
}
