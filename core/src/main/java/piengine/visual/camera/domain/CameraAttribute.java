package piengine.visual.camera.domain;

public class CameraAttribute {

    public final float fieldOfView;
    public final float nearPlane;
    public final float farPlane;

    public CameraAttribute(final float fieldOfView, final float nearPlane, final float farPlane) {
        this.fieldOfView = fieldOfView;
        this.nearPlane = nearPlane;
        this.farPlane = farPlane;
    }
}
