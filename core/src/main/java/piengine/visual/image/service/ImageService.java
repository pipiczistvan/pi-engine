package piengine.visual.image.service;

import org.joml.Vector2i;
import piengine.core.base.resource.SupplierService;
import piengine.visual.image.accessor.ImageAccessor;
import piengine.visual.image.domain.Image;
import piengine.visual.image.domain.ImageDao;
import piengine.visual.image.domain.ImageData;
import piengine.visual.image.domain.ImageKey;
import piengine.visual.image.interpreter.ImageInterpreter;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

@Component
public class ImageService extends SupplierService<ImageKey, ImageData, ImageDao, Image> {

    @Wire
    public ImageService(final ImageAccessor imageAccessor, final ImageInterpreter imageInterpreter) {
        super(imageAccessor, imageInterpreter);
    }

    @Override
    protected Image createDomain(final ImageDao dao, final ImageData resource) {
        return new Image(dao, new Vector2i(resource.width, resource.height));
    }
}
