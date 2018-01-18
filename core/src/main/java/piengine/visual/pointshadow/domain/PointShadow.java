package piengine.visual.pointshadow.domain;

import org.joml.Vector2i;
import piengine.core.base.domain.Domain;
import piengine.visual.camera.domain.Camera;
import piengine.visual.camera.domain.CameraAttribute;
import piengine.visual.camera.domain.FirstPersonCamera;
import piengine.visual.framebuffer.domain.Framebuffer;
import piengine.visual.light.domain.Light;

import static piengine.visual.camera.domain.ProjectionType.PERSPECTIVE;

public class PointShadow implements Domain<PointShadowDao> {

    private final PointShadowDao dao;
    private final Framebuffer shadowMap;
    private final Camera[] lightCameras = new Camera[6];

    public PointShadow(final PointShadowDao dao, final Light light, final Framebuffer shadowMap, final Vector2i resolution) {
        this.dao = dao;
        this.shadowMap = shadowMap;

        for (int i = 0; i < lightCameras.length; i++) {
            lightCameras[i] = new FirstPersonCamera(light, resolution, new CameraAttribute(90, 0.1f, 25), PERSPECTIVE);
        }

        lightCameras[0].setRotation(90, 0, 0);
        lightCameras[1].setRotation(-90, 0, 0);
        lightCameras[2].setRotation(0, 90, 0);
        lightCameras[3].setRotation(0, -90, 0);
        lightCameras[4].setRotation(180, 0, 0);
        lightCameras[5].setRotation(0, 0, 0);
    }

    @Override
    public PointShadowDao getDao() {
        return dao;
    }

    public Framebuffer getShadowMap() {
        return shadowMap;
    }

    public Camera getCamera(final int index) {
        return lightCameras[index];
    }
}
