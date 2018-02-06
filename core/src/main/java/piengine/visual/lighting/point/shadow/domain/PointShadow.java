package piengine.visual.lighting.point.shadow.domain;

import org.joml.Vector2i;
import org.joml.Vector3f;
import piengine.core.base.domain.Domain;
import piengine.io.interpreter.framebuffer.Framebuffer;
import piengine.object.camera.domain.Camera;
import piengine.object.camera.domain.CameraAttribute;
import piengine.object.camera.domain.FirstPersonCamera;
import piengine.visual.lighting.Light;
import piengine.visual.lighting.Shadow;

import static piengine.core.base.type.property.ApplicationProperties.get;
import static piengine.core.base.type.property.PropertyKeys.LIGHTING_POINT_SHADOW_DISTANCE;
import static piengine.object.camera.domain.ProjectionType.PERSPECTIVE;

public class PointShadow implements Shadow, Domain {

    public static final int CAMERA_COUNT = 6;

    private final Framebuffer shadowMap;
    private final Camera[] lightCameras = new Camera[CAMERA_COUNT];

    public PointShadow(final Framebuffer shadowMap) {
        this.shadowMap = shadowMap;
    }

    public void initialize(final Light light) {
        for (int i = 0; i < lightCameras.length; i++) {
            lightCameras[i] = new FirstPersonCamera(light, new Vector2i(shadowMap.width, shadowMap.height), new CameraAttribute(90, 1, get(LIGHTING_POINT_SHADOW_DISTANCE)), PERSPECTIVE);
        }

        lightCameras[0].setRotation(90, 0, 180); // RIGHT
        lightCameras[1].setRotation(-90, 0, 180); // LEFT
        lightCameras[2].setRotation(0, 90, 0); // TOP
        lightCameras[3].setRotation(0, -90, 0); // BOTTOM
        lightCameras[4].setRotation(180, 0, 180); // FORWARD
        lightCameras[5].setRotation(0, 0, 180); // BACKWARD
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
