package piengine.visual.skybox.service;

import piengine.core.base.resource.SupplierService;
import piengine.visual.skybox.accessor.SkyboxAccessor;
import piengine.visual.skybox.domain.Skybox;
import piengine.visual.skybox.domain.SkyboxDao;
import piengine.visual.skybox.domain.SkyboxData;
import piengine.visual.skybox.domain.SkyboxKey;
import piengine.visual.skybox.interpreter.SkyboxInterpreter;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

@Component
public class SkyboxService extends SupplierService<SkyboxKey, SkyboxData, SkyboxDao, Skybox> {

    @Wire
    public SkyboxService(final SkyboxAccessor skyboxAccessor, final SkyboxInterpreter skyboxInterpreter) {
        super(skyboxAccessor, skyboxInterpreter);
    }

    @Override
    protected Skybox createDomain(final SkyboxDao dao, final SkyboxData resource) {
        return new Skybox(dao, resource.cubeMap);
    }
}
