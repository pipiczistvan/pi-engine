package piengine.visual.image.service;

import piengine.core.base.resource.SupplierService;
import piengine.visual.image.accessor.ImageAccessor;
import piengine.visual.image.domain.Image;
import piengine.visual.image.domain.ImageDao;
import piengine.visual.image.domain.ImageData;
import piengine.visual.image.interpreter.ImageInterpreter;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

@Component
public class ImageService extends SupplierService<Image, ImageDao, ImageData> {

    @Wire
    public ImageService(final ImageAccessor imageAccessor, final ImageInterpreter imageInterpreter) {
        super(imageAccessor, imageInterpreter);
    }

    @Override
    protected Image createDomain(final ImageDao dao, final ImageData resource) {
        return new Image(dao);
    }
}
