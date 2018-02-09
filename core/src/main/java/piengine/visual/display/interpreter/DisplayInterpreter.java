package piengine.visual.display.interpreter;

import org.joml.Vector2f;
import org.joml.Vector2i;
import piengine.core.base.event.Event;
import piengine.core.base.exception.PIEngineException;
import piengine.core.input.service.InputService;
import piengine.core.time.service.TimeService;
import piengine.visual.display.domain.AwtFrame;
import piengine.visual.display.domain.Display;
import piengine.visual.display.domain.DisplayEventType;
import piengine.visual.display.domain.GlfwWindow;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import static piengine.core.base.type.property.ApplicationProperties.get;
import static piengine.core.base.type.property.PropertyKeys.DISPLAY_MODE;

@Component
public class DisplayInterpreter {

    private final Display display;

    @Wire
    public DisplayInterpreter(final TimeService timeService, final InputService inputService) {
        switch (get(DISPLAY_MODE)) {
            case AWT:
                this.display = new AwtFrame(timeService);
                break;
            case GLFW:
                this.display = new GlfwWindow(timeService, inputService);
                break;
            default:
                throw new PIEngineException("Could not create window!");
        }
    }

    public void addEvent(final DisplayEventType type, final Event event) {
        display.addEvent(type, event);
    }

    // Interventions

    public void createDisplay() {
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
