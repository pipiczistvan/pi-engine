package piengine.object.mesh.domain;

import piengine.core.base.domain.Domain;

public class Mesh implements Domain<MeshDao> {

    private final MeshDao dao;

    public Mesh(final MeshDao dao) {
        this.dao = dao;
    }

    @Override
    public MeshDao getDao() {
        return dao;
    }
}
