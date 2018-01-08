package piengine.object.model.manager;

import piengine.core.base.type.color.Color;
import piengine.object.entity.domain.Entity;
import piengine.object.model.domain.Model;
import piengine.object.model.domain.ModelAttribute;
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

    public Model supply(final String file, final Entity parent) {
        return supply(file, parent, new Color(1));
    }

    public Model supply(final String file, final Entity parent, final Texture texture) {
        return supply(file, parent, texture, new Color(1));
    }

    public Model supply(final String file, final Entity parent, final Color color) {
        return supply(file, parent, null, color);
    }

    public Model supply(final String file, final Entity parent, final String texture) {
        Image image = imageManager.supply(texture);
        return supply(file, parent, image);
    }

    public Model supply(final String file, final Entity parent, final Texture texture, final Color color) {
        return modelService.supply(file, parent, new ModelAttribute(texture, color));
    }
}
