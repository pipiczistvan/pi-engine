package piengine.visual.cubemap.manager;

import piengine.visual.cubemap.domain.CubeMap;
import piengine.visual.cubemap.domain.CubeMapKey;
import piengine.visual.cubemap.service.CubeMapService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

@Component
public class CubeMapManager {

    private final CubeMapService cubeMapService;

    @Wire
    public CubeMapManager(final CubeMapService cubeMapService) {
        this.cubeMapService = cubeMapService;
    }

    public CubeMap supply(final CubeMapKey key) {
        return cubeMapService.supply(key);
    }
}
