package piengine.object.model.manager;

import piengine.core.base.type.color.Color;
import piengine.core.utils.ColorUtils;
import piengine.object.entity.domain.Entity;
import piengine.object.model.domain.Model;
import piengine.object.model.domain.ModelKey;
import piengine.object.model.service.ModelService;
import piengine.visual.image.domain.Image;
import piengine.visual.image.manager.ImageManager;
import piengine.visual.texture.domain.Texture;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

@Component
public class ModelManager {

    private final ModelService modelService;
    private final ImageManager imageManager;

    @Wire
    public ModelManager(final ModelService modelService, final ImageManager imageManager) {
        this.modelService = modelService;
        this.imageManager = imageManager;
    }

    public Model supply(final Entity parent, final String meshFile, final Texture texture, final Color color, final boolean lightEmitter) {
        return modelService.supply(new ModelKey(parent, meshFile, texture, color, lightEmitter));
    }

    public Model supply(final Entity parent, final String meshFile, final Texture texture, final boolean lightEmitter) {
        return supply(parent, meshFile, texture, ColorUtils.WHITE, lightEmitter);
    }

    public Model supply(final Entity parent, final String meshFile, final Color color, final String imageFile, final boolean lightEmitter) {
        Image image = imageManager.supply(imageFile);
        return supply(parent, meshFile, image, color, lightEmitter);
    }

    public Model supply(final Entity parent, final String meshFile, final String imageFile, final boolean lightEmitter) {
        return supply(parent, meshFile, ColorUtils.WHITE, imageFile, lightEmitter);
    }

    public Model supply(final Entity parent, final String meshFile, final boolean lightEmitter) {
        return supply(parent, meshFile, null, ColorUtils.WHITE, lightEmitter);
    }
}
