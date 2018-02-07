package piengine.visual.window.service;

import org.joml.Vector2f;
import piengine.core.base.event.Event;
import piengine.visual.window.domain.WindowEventType;
import piengine.visual.window.interpreter.WindowInterpreter;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

@Component
public class WindowService {

    private final WindowInterpreter windowInterpreter;

    @Wire
    public WindowService(WindowInterpreter windowInterpreter) {
        this.windowInterpreter = windowInterpreter;
    }

    public void createWindow() {
        windowInterpreter.createWindow();
    }

    public void addEvent(WindowEventType type, Event event) {
        windowInterpreter.addEvent(type, event);
    }

    public Vector2f getPointer() {
        return windowInterpreter.getPointer();
    }

    public void setPointer(Vector2f position) {
        windowInterpreter.setPointer(position);
    }

    public Vector2f getWindowCenter() {
        return windowInterpreter.getWindowCenter();
    }

    public void swapBuffers() {
        windowInterpreter.swapBuffers();
    }

    public void closeWindow() {
        windowInterpreter.closeWindow();
    }

    public int getWidth() {
        return windowInterpreter.getWidth();
    }

    public int getHeight() {
        return windowInterpreter.getHeight();
    }

    public int getOldWidth() {
        return windowInterpreter.getOldWidth();
    }

    public int getOldHeight() {
        return windowInterpreter.getOldHeight();
    }

    public void setCursorVisibility(final boolean visible) {
        windowInterpreter.setCursorVisibility(visible);
    }
}
