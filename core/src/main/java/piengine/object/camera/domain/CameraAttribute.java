package piengine.object.camera.domain;

public class CameraAttribute {

    public float fieldOfView;
    public float nearPlane;
    public float farPlane;

    public CameraAttribute(final float fieldOfView, final float nearPlane, final float farPlane) {
        this.fieldOfView = fieldOfView;
        this.nearPlane = nearPlane;
        this.farPlane = farPlane;
    }
}
