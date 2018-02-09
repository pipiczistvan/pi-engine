package piengine.visual.display.service;

import org.joml.Vector2f;
import piengine.core.base.event.Event;
import piengine.visual.display.domain.DisplayEventType;
import piengine.visual.display.interpreter.DisplayInterpreter;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

@Component
public class DisplayService {

    private final DisplayInterpreter displayInterpreter;

    @Wire
    public DisplayService(final DisplayInterpreter displayInterpreter) {
        this.displayInterpreter = displayInterpreter;
    }

    public void createDisplay() {
        displayInterpreter.createDisplay();
    }

    public void addEvent(final DisplayEventType type, final Event event) {
        displayInterpreter.addEvent(type, event);
    }

    public void render() {
        displayInterpreter.render();
    }

    public Vector2f getPointer() {
        return displayInterpreter.getPointer();
    }

    public void setPointer(Vector2f position) {
        displayInterpreter.setPointer(position);
    }

    public Vector2f getDisplayCenter() {
        return displayInterpreter.getDisplayCenter();
    }

    public void closeDisplay() {
        displayInterpreter.closeDisplay();
    }

    public int getWidth() {
        return displayInterpreter.getWidth();
    }

    public int getHeight() {
        return displayInterpreter.getHeight();
    }

    public int getOldWidth() {
        return displayInterpreter.getOldWidth();
    }

    public int getOldHeight() {
        return displayInterpreter.getOldHeight();
    }

    public void setCursorVisibility(final boolean visible) {
        displayInterpreter.setCursorVisibility(visible);
    }
}
