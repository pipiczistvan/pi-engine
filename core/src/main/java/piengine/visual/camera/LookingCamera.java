package piengine.visual.camera;

import org.joml.Vector2f;
import org.joml.Vector2i;
import piengine.object.entity.domain.Entity;
import piengine.visual.framebuffer.domain.FrameBuffer;

import static piengine.core.property.domain.ApplicationProperties.get;
import static piengine.core.property.domain.PropertyKeys.CAMERA_LOOK_SPEED;
import static piengine.core.utils.MatrixUtils.VIEW_MATRIX;
import static piengine.core.utils.MatrixUtils.VIEW_MATRIX_REVERSE;

public class LookingCamera extends Camera {

    private final float lookSpeed;

    public LookingCamera(Entity parent, Vector2i viewport, float fieldOfView, float nearPlane, float farPlane, ProjectionType projectionType) {
        super(parent, viewport, fieldOfView, nearPlane, farPlane, projectionType);

        this.lookSpeed = get(CAMERA_LOOK_SPEED);
    }

    @Override
    public void update(double delta) {
        VIEW_MATRIX_REVERSE(position, rotation).mul(VIEW_MATRIX(parent.getPosition(), parent.getRotation()), this.view);
    }

    public void lookAt(Vector2f lookAt) {
        rotation.add(lookAt.x * lookSpeed, -lookAt.y * lookSpeed, 0.0f);

        clampRotation();
    }

}
