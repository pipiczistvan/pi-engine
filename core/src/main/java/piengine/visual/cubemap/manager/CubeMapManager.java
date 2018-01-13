package piengine.visual.cubemap.manager;

import piengine.visual.cubemap.domain.CubeMap;
import piengine.visual.cubemap.domain.CubeMapKey;
import piengine.visual.cubemap.service.CubeMapService;
import piengine.visual.image.domain.ImageKey;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

@Component
public class CubeMapManager {

    private final CubeMapService cubeMapService;

    @Wire
    public CubeMapManager(final CubeMapService cubeMapService) {
        this.cubeMapService = cubeMapService;
    }

    public CubeMap supply(final String rightImage, final String leftImage, final String topImage,
                          final String bottomImage, final String backImage, final String frontImage) {
        return cubeMapService.supply(new CubeMapKey(
                new ImageKey(rightImage), new ImageKey(leftImage), new ImageKey(topImage),
                new ImageKey(bottomImage), new ImageKey(backImage), new ImageKey(frontImage)
        ));
    }
}
