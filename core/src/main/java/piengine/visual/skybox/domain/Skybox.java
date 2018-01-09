package piengine.visual.skybox.domain;

import piengine.core.base.domain.Domain;
import piengine.visual.cubemap.domain.CubeMap;

public class Skybox implements Domain<SkyboxDao> {

    public final CubeMap cubeMap;
    private final SkyboxDao dao;

    public Skybox(final SkyboxDao dao, final CubeMap cubeMap) {
        this.dao = dao;
        this.cubeMap = cubeMap;
    }

    @Override
    public SkyboxDao getDao() {
        return dao;
    }
}
