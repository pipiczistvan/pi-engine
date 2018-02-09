package piengine.visual.display.interpreter;

import org.joml.Vector2f;
import piengine.core.base.event.Event;
import piengine.core.input.service.InputService;
import piengine.visual.display.domain.DisplayEventType;
import piutils.map.ListMap;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import static org.lwjgl.opengl.GL.createCapabilities;
import static piengine.core.base.type.property.ApplicationProperties.get;
import static piengine.core.base.type.property.PropertyKeys.DISPLAY_IN_CANVAS;
import static piengine.visual.display.domain.DisplayEventType.CLOSE;
import static piengine.visual.display.domain.DisplayEventType.INITIALIZE;
import static piengine.visual.display.domain.DisplayEventType.UPDATE;

@Component
public class DisplayInterpreter {

    private final InputService inputService;
    private final ListMap<DisplayEventType, Event> eventMap;

    private Display display;

    @Wire
    public DisplayInterpreter(final InputService inputService) {
        this.inputService = inputService;
        this.eventMap = new ListMap<>();
    }

    public void createDisplay() {
        display = get(DISPLAY_IN_CANVAS) ? new Frame(this) : new Window(inputService);
        display.initialize();
        //todo: ez frame esetén rossz
//        eventMap.get(INITIALIZE).forEach(Event::invoke);
        while (display.shouldUpdate()) {
            display.render();
//            eventMap.get(UPDATE).forEach(Event::invoke);
//            display.update(0);
//            if (display.isResized()) {
//                display.resize();
//                eventMap.get(RESIZE).forEach(Event::invoke);
//            }
        }
        eventMap.get(CLOSE).forEach(Event::invoke);
        display.terminate();
    }

    public void initializeDisplay() {
        createCapabilities(true);
        eventMap.get(INITIALIZE).forEach(Event::invoke);
        //todo: opengl context frame esetén csak így érhető el :|
    }

    public void updateDisplay() {
        eventMap.get(UPDATE).forEach(Event::invoke);
    }

    public void addEvent(final DisplayEventType type, final Event event) {
        eventMap.put(type, event);
    }

    public void render() {
        display.render();
    }

    public Vector2f getPointer() {
        return display.getPointer();
    }

    public void setPointer(final Vector2f position) {
        display.setPointer(position);
    }

    public Vector2f getDisplayCenter() {
        return display.getDisplayCenter();
    }

    public void closeDisplay() {
        display.closeDisplay();
    }

    public int getWidth() {
        return display.getWidth();
    }

    public int getHeight() {
        return display.getHeight();
    }

    public int getOldWidth() {
        return display.getOldWidth();
    }

    public int getOldHeight() {
        return display.getOldHeight();
    }

    public void setCursorVisibility(final boolean visible) {
        display.setCursorVisibility(visible);
    }
}
