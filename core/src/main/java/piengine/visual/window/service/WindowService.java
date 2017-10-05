package piengine.visual.window.service;

import org.joml.Vector2f;
import piengine.core.base.event.Event;
import piengine.visual.window.domain.WindowEventType;
import piengine.visual.window.interpreter.WindowInterpreter;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import static piengine.core.property.domain.ApplicationProperties.get;
import static piengine.core.property.domain.PropertyKeys.WINDOW_CURSOR_HIDDEN;
import static piengine.core.property.domain.PropertyKeys.WINDOW_FULL_SCREEN;
import static piengine.core.property.domain.PropertyKeys.WINDOW_HEIGHT;
import static piengine.core.property.domain.PropertyKeys.WINDOW_MAJOR_VERSION;
import static piengine.core.property.domain.PropertyKeys.WINDOW_MINOR_VERSION;
import static piengine.core.property.domain.PropertyKeys.WINDOW_MULTI_SAMPLE_COUNT;
import static piengine.core.property.domain.PropertyKeys.WINDOW_TITLE;
import static piengine.core.property.domain.PropertyKeys.WINDOW_WIDTH;

@Component
public class WindowService {

    private final WindowInterpreter windowInterpreter;

    @Wire
    public WindowService(WindowInterpreter windowInterpreter) {
        this.windowInterpreter = windowInterpreter;
    }

    public void createWindow() {
        windowInterpreter.createWindow(
                get(WINDOW_TITLE),
                get(WINDOW_WIDTH),
                get(WINDOW_HEIGHT),
                get(WINDOW_FULL_SCREEN),
                get(WINDOW_MULTI_SAMPLE_COUNT),
                get(WINDOW_CURSOR_HIDDEN),
                get(WINDOW_MAJOR_VERSION),
                get(WINDOW_MINOR_VERSION)
        );
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

}
