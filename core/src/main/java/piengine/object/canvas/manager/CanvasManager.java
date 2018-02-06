package piengine.object.canvas.manager;

import piengine.core.architecture.manager.SupplierManager;
import piengine.core.base.domain.Entity;
import piengine.core.base.type.color.Color;
import piengine.core.utils.ColorUtils;
import piengine.io.interpreter.framebuffer.Framebuffer;
import piengine.object.canvas.domain.Canvas;
import piengine.object.canvas.domain.CanvasKey;
import piengine.object.canvas.service.CanvasService;
import piengine.visual.postprocessing.domain.EffectType;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

@Component
public class CanvasManager extends SupplierManager<CanvasKey, Canvas> {

    @Wire
    public CanvasManager(final CanvasService canvasService) {
        super(canvasService);
    }

    public Canvas supply(final Entity parent, final Framebuffer framebuffer, final Color color, final EffectType... effects) {
        return supply(new CanvasKey(parent, framebuffer, color, effects));
    }

    public Canvas supply(final Entity parent, final Framebuffer framebuffer, final EffectType... effects) {
        return supply(parent, framebuffer, ColorUtils.WHITE, effects);
    }
}
