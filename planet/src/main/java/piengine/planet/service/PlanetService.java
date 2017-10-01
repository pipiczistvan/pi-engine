package piengine.planet.service;

import piengine.common.planet.domain.Planet;
import piengine.core.base.resource.SupplierService;
import piengine.object.entity.domain.Entity;
import piengine.object.mesh.domain.Mesh;
import piengine.object.mesh.domain.MeshDao;
import piengine.planet.accessor.PlanetAccessor;
import piengine.planet.domain.PlanetData;
import piengine.planet.interpreter.PlanetInterpreter;
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

    public Planet supply(final String file, final Entity parent) {
        Mesh mesh = supply(file);
        return new Planet(mesh, parent);
    }

}
