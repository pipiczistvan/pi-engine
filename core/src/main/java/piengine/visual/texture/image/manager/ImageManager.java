package piengine.visual.texture.image.manager;

import piengine.core.architecture.manager.SupplierManager;
import piengine.visual.texture.image.domain.Image;
import piengine.visual.texture.image.service.ImageService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

@Component
public class ImageManager extends SupplierManager<String, Image> {

    @Wire
    public ImageManager(final ImageService imageService) {
        super(imageService);
    }
}
