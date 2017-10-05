package piengine.visual.camera;

import org.joml.Vector2f;
import piengine.object.entity.domain.Entity;

import static piengine.core.property.domain.ApplicationProperties.get;
import static piengine.core.property.domain.PropertyKeys.CAMERA_LOOK_SPEED;
import static piengine.core.utils.MatrixUtils.VIEW_MATRIX;
import static piengine.core.utils.MatrixUtils.VIEW_MATRIX_REVERSE;
import static piengine.visual.camera.ProjectionType.ORTHOGRAPHIC;

public class LookingCamera extends Camera {

    private final float lookSpeed;

    public LookingCamera(Entity parent) {
        super(parent, ORTHOGRAPHIC);

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
