package piengine.visual.skybox.domain;

import org.joml.Matrix4f;
import piengine.core.base.domain.Domain;
import piengine.core.utils.VectorUtils;
import piengine.visual.camera.domain.Camera;
import piengine.visual.cubemap.domain.CubeMap;

public class Skybox implements Domain<SkyboxDao> {

    public final CubeMap cubeMap;
    private final SkyboxDao dao;
    private float rotation = 0;

    public Skybox(final SkyboxDao dao, final CubeMap cubeMap) {
        this.dao = dao;
        this.cubeMap = cubeMap;
    }

    @Override
    public SkyboxDao getDao() {
        return dao;
    }

    public Matrix4f getView(final Camera camera) {
        Matrix4f viewMatrix = new Matrix4f(camera.getView());

        viewMatrix.m30(0);
        viewMatrix.m31(0);
        viewMatrix.m32(0);
        viewMatrix.rotate((float) Math.toRadians(rotation), VectorUtils.UP);

        return viewMatrix;
    }

    public void addRotation(final float rotation) {
        this.rotation += rotation;
    }
}
