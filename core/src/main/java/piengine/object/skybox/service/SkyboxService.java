package piengine.object.skybox.service;

import piengine.core.base.resource.SupplierService;
import piengine.object.skybox.accessor.SkyboxAccessor;
import piengine.object.skybox.domain.Skybox;
import piengine.object.skybox.domain.SkyboxDao;
import piengine.object.skybox.domain.SkyboxData;
import piengine.object.skybox.domain.SkyboxKey;
import piengine.object.skybox.interpreter.SkyboxInterpreter;
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
