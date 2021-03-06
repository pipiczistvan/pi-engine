package piengine.object.skybox.manager;

import piengine.object.skybox.domain.Skybox;
import piengine.object.skybox.domain.SkyboxKey;
import piengine.object.skybox.service.SkyboxService;
import piengine.visual.cubemap.domain.CubeMap;
import piengine.visual.cubemap.manager.CubeMapManager;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

@Component
public class SkyboxManager {

    private final SkyboxService skyboxService;
    private final CubeMapManager cubeMapManager;

    @Wire
    public SkyboxManager(final SkyboxService skyboxService, final CubeMapManager cubeMapManager) {
        this.skyboxService = skyboxService;
        this.cubeMapManager = cubeMapManager;
    }

    public Skybox supply(final float size, final CubeMap cubeMap) {
        return skyboxService.supply(new SkyboxKey(size, cubeMap));
    }

    public Skybox supply(final float size, final String rightImage, final String leftImage,
                         final String topImage, final String bottomImage, final String backImage,
                         final String frontImage) {
        CubeMap cubeMap = cubeMapManager.supply(rightImage, leftImage, topImage, bottomImage, backImage, frontImage);
        return supply(size, cubeMap);
    }
}
