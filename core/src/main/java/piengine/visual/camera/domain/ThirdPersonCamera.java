package piengine.visual.camera.domain;

import org.joml.Matrix4f;
import org.joml.Vector2i;
import org.joml.Vector3f;
import piengine.object.entity.domain.Entity;

import static piengine.core.utils.MatrixUtils.VIEW_MATRIX;

public class ThirdPersonCamera extends Camera {

    private final Vector3f cameraPosition;
    //todo: nem dinamikus
    private float distance = 10;

    public ThirdPersonCamera(final Entity parent, final Vector2i viewport, final CameraAttribute attribute, final ProjectionType projectionType) {
        super(parent, viewport, attribute, projectionType);

        this.cameraPosition = new Vector3f();
    }

    @Override
    protected void calculateViewMatrix(final Matrix4f viewMatrix) {
        Vector3f position = getPosition();
        Vector3f rotation = getRotation();

        float horizontalDistance = calculateHorizontalDistance(-rotation.y);
        float verticalDistance = calculateVerticalDistance(-rotation.y);

        float theta = -rotation.x;
        float offsetX = (float) (horizontalDistance * Math.sin(Math.toRadians(theta)));
        float offsetZ = (float) (horizontalDistance * Math.cos(Math.toRadians(theta)));

        cameraPosition.x = position.x + offsetX;
        cameraPosition.y = position.y + verticalDistance;
        cameraPosition.z = position.z + offsetZ;

        VIEW_MATRIX(cameraPosition, rotation).mul(VIEW_MATRIX(parent.getPosition(), parent.getRotation()), viewMatrix);
    }

    private float calculateHorizontalDistance(final float pitch) {
        return (float) (distance * Math.cos(Math.toRadians(pitch)));
    }

    private float calculateVerticalDistance(final float pitch) {
        return (float) (distance * Math.sin(Math.toRadians(pitch)));
    }
}
