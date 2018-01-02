package piengine.visual.camera.domain;

public class CameraAttribute {

    final float fieldOfView;
    final float nearPlane;
    final float farPlane;
    final float lookUpLimit;
    final float lookDownLimit;
    final float lookSpeed;
    final float moveSpeed;
    final float strafeSpeed;

    public CameraAttribute(final float fieldOfView, final float nearPlane, final float farPlane, final float lookUpLimit, final float lookDownLimit, final float lookSpeed, float moveSpeed) {
        this.fieldOfView = fieldOfView;
        this.nearPlane = nearPlane;
        this.farPlane = farPlane;
        this.lookUpLimit = lookUpLimit;
        this.lookDownLimit = lookDownLimit;
        this.lookSpeed = lookSpeed;
        this.moveSpeed = moveSpeed;
        this.strafeSpeed = moveSpeed / (float) Math.sqrt(2);
    }
}
