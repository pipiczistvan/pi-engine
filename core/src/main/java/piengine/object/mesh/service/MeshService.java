package piengine.object.mesh.service;

import piengine.core.base.resource.SupplierService;
import piengine.object.mesh.accessor.MeshAccessor;
import piengine.object.mesh.domain.Mesh;
import piengine.object.mesh.domain.MeshDao;
import piengine.object.mesh.domain.MeshData;
import piengine.object.mesh.domain.MeshKey;
import piengine.object.mesh.interpreter.MeshInterpreter;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

@Component
public class MeshService extends SupplierService<MeshKey, MeshData, MeshDao, Mesh> {

    @Wire
    public MeshService(final MeshAccessor meshAccessor, final MeshInterpreter meshInterpreter) {
        super(meshAccessor, meshInterpreter);
    }

    @Override
    protected Mesh createDomain(final MeshDao dao, final MeshData resource) {
        return new Mesh(dao);
    }

}
