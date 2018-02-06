package piengine.object.skybox.manager;

import piengine.core.architecture.manager.SupplierManager;
import piengine.object.skybox.domain.Skybox;
import piengine.object.skybox.service.SkyboxService;
import piengine.visual.texture.cubeimage.domain.CubeImage;
import piengine.visual.texture.cubeimage.domain.CubeImageKey;
import piengine.visual.texture.cubeimage.manager.CubeImageManager;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

@Component
public class SkyboxManager extends SupplierManager<CubeImage, Skybox> {

    private CubeImageManager cubeImageManager;

    @Wire
    public SkyboxManager(final SkyboxService skyboxService, final CubeImageManager cubeImageManager) {
        super(skyboxService);
        this.cubeImageManager = cubeImageManager;
    }

    public Skybox supply(final String rightImage, final String leftImage, final String topImage,
                         final String bottomImage, final String backImage, final String frontImage) {
        CubeImage cubeImage = cubeImageManager.supply(
                new CubeImageKey(rightImage, leftImage, topImage, bottomImage, backImage, frontImage));

        return supply(cubeImage);
    }
}
