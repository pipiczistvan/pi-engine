package piengine.visual.display.manager;

import org.joml.Vector2f;
import piengine.visual.display.service.DisplayService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

@Component
public class DisplayManager {

    private final DisplayService displayService;

    @Wire
    public DisplayManager(final DisplayService displayService) {
        this.displayService = displayService;
    }

    public void closeDisplay() {
        displayService.closeDisplay();
    }

    public Vector2f getPointer() {
        return displayService.getPointer();
    }

    public void setPointer(Vector2f position) {
        displayService.setPointer(position);
    }

    public Vector2f getDisplayCenter() {
        return displayService.getDisplayCenter();
    }

    public void setCursorVisibility(final boolean visible) {
        displayService.setCursorVisibility(visible);
    }

    public int getWidth() {
        return displayService.getWidth();
    }

    public int getHeight() {
        return displayService.getHeight();
    }
}
