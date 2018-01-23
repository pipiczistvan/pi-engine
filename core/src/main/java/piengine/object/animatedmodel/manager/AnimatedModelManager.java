package piengine.object.animatedmodel.manager;

import piengine.object.animatedmodel.domain.AnimatedModel;
import piengine.object.animatedmodel.domain.AnimatedModelKey;
import piengine.object.animatedmodel.service.AnimatedModelService;
import piengine.object.entity.domain.Entity;
import piengine.visual.image.domain.Image;
import piengine.visual.image.manager.ImageManager;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

@Component
public class AnimatedModelManager {

    //todo: dynamic
    private static final int MAX_WIGHTS = 3;

    private final AnimatedModelService animatedModelService;
    private final ImageManager imageManager;

    @Wire
    public AnimatedModelManager(final AnimatedModelService animatedModelService, final ImageManager imageManager) {
        this.animatedModelService = animatedModelService;
        this.imageManager = imageManager;
    }

    public AnimatedModel supply(final Entity parent, final String colladaFile, final String imageFile) {
        Image image = imageManager.supply(imageFile);

        return animatedModelService.supply(new AnimatedModelKey(parent, colladaFile, image, MAX_WIGHTS));
    }
}
