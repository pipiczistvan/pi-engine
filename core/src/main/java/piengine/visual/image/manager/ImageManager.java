package piengine.visual.image.manager;

import piengine.visual.image.domain.Image;
import piengine.visual.image.domain.ImageKey;
import piengine.visual.image.service.ImageService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

@Component
public class ImageManager {

    private final ImageService imageService;

    @Wire
    public ImageManager(final ImageService imageService) {
        this.imageService = imageService;
    }

    public Image supply(final String file) {
        return imageService.supply(new ImageKey(file));
    }
}
