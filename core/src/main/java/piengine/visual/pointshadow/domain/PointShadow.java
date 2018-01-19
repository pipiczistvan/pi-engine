package piengine.visual.pointshadow.domain;

import org.joml.Vector2i;
import piengine.core.base.domain.Domain;
import piengine.visual.camera.domain.Camera;
import piengine.visual.camera.domain.CameraAttribute;
import piengine.visual.camera.domain.FirstPersonCamera;
import piengine.visual.framebuffer.domain.Framebuffer;
import piengine.visual.light.domain.Light;

import static piengine.core.base.type.property.ApplicationProperties.get;
import static piengine.core.base.type.property.PropertyKeys.POINT_SHADOW_FAR_PLANE;
import static piengine.visual.camera.domain.ProjectionType.PERSPECTIVE;

public class PointShadow implements Domain<PointShadowDao> {

    public static final int CAMERA_COUNT = 6;

    private final PointShadowDao dao;
    private final Framebuffer shadowMap;
    private final Light light;
    private final Camera[] lightCameras = new Camera[CAMERA_COUNT];

    public PointShadow(final PointShadowDao dao, final Light light, final Framebuffer shadowMap, final Vector2i resolution) {
        this.dao = dao;
        this.shadowMap = shadowMap;
        this.light = light;

        for (int i = 0; i < lightCameras.length; i++) {
            lightCameras[i] = new FirstPersonCamera(light, resolution, new CameraAttribute(90, 1, get(POINT_SHADOW_FAR_PLANE)), PERSPECTIVE);
        }

        lightCameras[0].setRotation(90, 0, 180); // RIGHT
        lightCameras[1].setRotation(-90, 0, 180); // LEFT
        lightCameras[2].setRotation(0, 90, 0); // TOP
        lightCameras[3].setRotation(0, -90, 0); // BOTTOM
        lightCameras[4].setRotation(180, 0, 180); // FORWARD
        lightCameras[5].setRotation(0, 0, 180); // BACKWARD
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

    public Light getLight() {
        return light;
    }
}
