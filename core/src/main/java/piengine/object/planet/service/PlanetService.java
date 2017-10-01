package piengine.object.planet.service;

import piengine.core.base.resource.SupplierService;
import piengine.object.mesh.domain.Mesh;
import piengine.object.mesh.domain.MeshDao;
import piengine.object.planet.accessor.PlanetAccessor;
import piengine.object.planet.domain.PlanetData;
import piengine.object.planet.interpreter.PlanetInterpreter;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

@Component
public class PlanetService extends SupplierService<Mesh, MeshDao, PlanetData> {

    @Wire
    public PlanetService(final PlanetAccessor accessor, final PlanetInterpreter interpreter) {
        super(accessor, interpreter);
    }

    @Override
    protected Mesh createDomain(final MeshDao dao, final PlanetData resource) {
        return new Mesh(dao);
    }

}
