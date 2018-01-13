package piengine.object.canvas.manager;

import piengine.core.base.type.color.Color;
import piengine.core.utils.ColorUtils;
import piengine.object.canvas.domain.Canvas;
import piengine.object.canvas.domain.CanvasKey;
import piengine.object.canvas.service.CanvasService;
import piengine.object.entity.domain.Entity;
import piengine.visual.image.domain.Image;
import piengine.visual.image.manager.ImageManager;
import piengine.visual.texture.domain.Texture;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

@Component
public class CanvasManager {

    private final CanvasService canvasService;
    private final ImageManager imageManager;

    @Wire
    public CanvasManager(final CanvasService canvasService, final ImageManager imageManager) {
        this.canvasService = canvasService;
        this.imageManager = imageManager;
    }

    public Canvas supply(final Entity parent, final Texture texture, final Color color) {
        return canvasService.supply(new CanvasKey(parent, texture, color));
    }

    public Canvas supply(final Entity parent, final Texture texture) {
        return supply(parent, texture, ColorUtils.WHITE);
    }

    public Canvas supply(final Entity parent, final String imageFile, final Color color) {
        Image image = imageManager.supply(imageFile);
        return supply(parent, image, color);
    }

    public Canvas supply(final Entity parent, final String imageFile) {
        return supply(parent, imageFile, ColorUtils.WHITE);
    }
}
