package piengine.visual.lighting.point.shadow.domain;

import org.joml.Vector3f;
import piengine.core.base.domain.Domain;
import piengine.visual.camera.domain.Camera;
import piengine.visual.camera.domain.CameraAttribute;
import piengine.visual.camera.domain.FirstPersonCamera;
import piengine.visual.framebuffer.domain.Framebuffer;
import piengine.visual.lighting.Light;
import piengine.visual.lighting.Shadow;

import static piengine.core.base.type.property.ApplicationProperties.get;
import static piengine.core.base.type.property.PropertyKeys.LIGHTING_POINT_SHADOW_DISTANCE;
import static piengine.visual.camera.domain.ProjectionType.PERSPECTIVE;

public class PointShadow implements Shadow, Domain<PointShadowDao> {

    public static final int CAMERA_COUNT = 6;

    private final PointShadowDao dao;
    private final Framebuffer shadowMap;
    private final Camera[] lightCameras = new Camera[CAMERA_COUNT];

    public PointShadow(final PointShadowDao dao, final Framebuffer shadowMap) {
        this.dao = dao;
        this.shadowMap = shadowMap;
    }

    public void initialize(final Light light) {
        for (int i = 0; i < lightCameras.length; i++) {
            lightCameras[i] = new FirstPersonCamera(light, shadowMap.resolution, new CameraAttribute(90, 1, get(LIGHTING_POINT_SHADOW_DISTANCE)), PERSPECTIVE);
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

    public Vector3f getPosition() {
        return lightCameras[0].getPosition();
    }
}
