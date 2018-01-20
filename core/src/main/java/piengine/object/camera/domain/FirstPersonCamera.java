package piengine.object.camera.domain;

import org.joml.Matrix4f;
import org.joml.Vector2i;
import piengine.object.entity.domain.Entity;

import static piengine.core.utils.MatrixUtils.VIEW_MATRIX;

public class FirstPersonCamera extends Camera {

    public FirstPersonCamera(final Entity parent, final Vector2i viewport, final CameraAttribute attribute, final ProjectionType projectionType) {
        super(parent, viewport, attribute, projectionType);
    }

    @Override
    protected void calculateViewMatrix(final Matrix4f viewMatrix) {
        VIEW_MATRIX(getPosition(), getRotation(), viewMatrix);
    }
}
