package piengine.visual.camera.domain;

import org.joml.Matrix4f;
import org.joml.Vector2i;
import piengine.object.entity.domain.Entity;

import static piengine.core.utils.MatrixUtils.VIEW_MATRIX;
import static piengine.core.utils.MatrixUtils.VIEW_MATRIX_REVERSE;

public class ThirdPersonCamera extends Camera {

    public ThirdPersonCamera(final Entity parent, final Vector2i viewport, final float fieldOfView,
                             final float nearPlane, final float farPlane, final float lookUpLimit,
                             final float lookDownLimit, final float lookSpeed, final float moveSpeed,
                             final ProjectionType projectionType) {

        super(parent, viewport, fieldOfView, nearPlane, farPlane, lookUpLimit, lookDownLimit, lookSpeed, moveSpeed, projectionType);
    }

    @Override
    protected void calculateViewMatrix(final Matrix4f viewMatrix) {
        VIEW_MATRIX_REVERSE(position, rotation).mul(VIEW_MATRIX(parent.getPosition(), parent.getRotation()), viewMatrix);
    }
}
