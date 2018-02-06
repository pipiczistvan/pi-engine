package piengine.visual.texture.cubeimage.manager;

import piengine.core.architecture.manager.SupplierManager;
import piengine.visual.texture.cubeimage.domain.CubeImage;
import piengine.visual.texture.cubeimage.domain.CubeImageKey;
import piengine.visual.texture.cubeimage.service.CubeImageService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

@Component
public class CubeImageManager extends SupplierManager<CubeImageKey, CubeImage> {

    @Wire
    public CubeImageManager(final CubeImageService cubeImageService) {
        super(cubeImageService);
    }
}
