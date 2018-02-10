package piengine.visual.display.manager;

import org.joml.Vector2f;
import org.joml.Vector2i;
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

    // Interventions

    public void closeDisplay() {
        displayService.closeDisplay();
    }

    public void setCursorVisibility(final boolean visible) {
        displayService.setCursorVisibility(visible);
    }

    public void setPointer(final Vector2f position) {
        displayService.setPointer(position);
    }

    // Window and viewport

    public Vector2i getWindowSize() {
        return displayService.getWindowSize();
    }

    public Vector2i getOldWindowSize() {
        return displayService.getOldWindowSize();
    }

    public Vector2i getViewport() {
        return displayService.getViewport();
    }

    public Vector2i getOldViewport() {
        return displayService.getOldViewport();
    }

    public Vector2f getViewportCenter() {
        return displayService.getViewportCenter();
    }
}
