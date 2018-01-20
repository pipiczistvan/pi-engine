package piengine.visual.cubemap.service;

import org.joml.Vector2i;
import piengine.core.base.resource.SupplierService;
import piengine.visual.cubemap.accessor.CubeMapAccessor;
import piengine.visual.cubemap.domain.CubeMap;
import piengine.visual.cubemap.domain.CubeMapDao;
import piengine.visual.cubemap.domain.CubeMapData;
import piengine.visual.cubemap.domain.CubeMapKey;
import piengine.visual.cubemap.interpreter.CubeMapInterpreter;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

@Component
public class CubeMapService extends SupplierService<CubeMapKey, CubeMapData, CubeMapDao, CubeMap> {

    @Wire
    public CubeMapService(final CubeMapAccessor cubeMapAccessor, final CubeMapInterpreter cubeMapInterpreter) {
        super(cubeMapAccessor, cubeMapInterpreter);
    }

    @Override
    protected CubeMap createDomain(final CubeMapDao dao, final CubeMapData resource) {
        return new CubeMap(dao, new Vector2i(resource.textureData[0].width, resource.textureData[0].height));
    }
}
