package piengine.object.canvas.manager;

import piengine.object.canvas.domain.Canvas;
import piengine.object.canvas.domain.CanvasKey;
import piengine.object.canvas.service.CanvasService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

@Component
public class CanvasManager {

    private final CanvasService canvasService;

    @Wire
    public CanvasManager(final CanvasService canvasService) {
        this.canvasService = canvasService;
    }

    public Canvas supply(final CanvasKey key) {
        return canvasService.supply(key);
    }
}
