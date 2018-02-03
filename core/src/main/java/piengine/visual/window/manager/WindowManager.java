package piengine.visual.window.manager;

import org.joml.Vector2f;
import piengine.visual.window.service.WindowService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

@Component
public class WindowManager {

    private final WindowService windowService;

    @Wire
    public WindowManager(final WindowService windowService) {
        this.windowService = windowService;
    }

    public void closeWindow() {
        windowService.closeWindow();
    }

    public Vector2f getPointer() {
        return windowService.getPointer();
    }

    public void setPointer(Vector2f position) {
        windowService.setPointer(position);
    }

    public Vector2f getWindowCenter() {
        return windowService.getWindowCenter();
    }

    public void setCursorVisibility(final boolean visible) {
        windowService.setCursorVisibility(visible);
    }

    public int getWidth() {
        return windowService.getWidth();
    }

    public int getHeight() {
        return windowService.getHeight();
    }
}
