package piengine.object.animatedmodel.manager;

import piengine.core.architecture.manager.SupplierManager;
import piengine.core.base.domain.Entity;
import piengine.object.animatedmodel.domain.AnimatedModel;
import piengine.object.animatedmodel.domain.AnimatedModelKey;
import piengine.object.animatedmodel.service.AnimatedModelService;
import piengine.visual.texture.image.domain.Image;
import piengine.visual.texture.image.manager.ImageManager;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

@Component
public class AnimatedModelManager extends SupplierManager<AnimatedModelKey, AnimatedModel> {

    private final ImageManager imageManager;

    @Wire
    public AnimatedModelManager(final AnimatedModelService animatedModelService, final ImageManager imageManager) {
        super(animatedModelService);
        this.imageManager = imageManager;
    }

    public AnimatedModel supply(final Entity parent, final String colladaFile, final String imageFile) {
        Image image = imageManager.supply(imageFile);

        return supply(new AnimatedModelKey(parent, colladaFile, image));
    }
}
