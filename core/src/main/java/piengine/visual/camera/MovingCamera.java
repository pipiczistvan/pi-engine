package piengine.visual.camera;

import org.joml.Vector2f;
import piengine.object.entity.domain.Entity;

import static piengine.core.property.domain.ApplicationProperties.get;
import static piengine.core.property.domain.PropertyKeys.CAMERA_LOOK_DOWN_LIMIT;
import static piengine.core.property.domain.PropertyKeys.CAMERA_LOOK_SPEED;
import static piengine.core.property.domain.PropertyKeys.CAMERA_LOOK_UP_LIMIT;
import static piengine.core.property.domain.PropertyKeys.CAMERA_MOVE_SPEED;

public class MovingCamera extends Camera {

    private final float lookUpLimit;
    private final float lookDownLimit;
    private final float lookSpeed;
    private final float moveSpeed;
    private final float strafeSpeed;

    private final Vector2f movement;

    public MovingCamera(Entity parent, ProjectionType projectionType) {
        super(parent, projectionType);

        this.lookDownLimit = get(CAMERA_LOOK_DOWN_LIMIT);
        this.lookUpLimit = get(CAMERA_LOOK_UP_LIMIT);
        this.lookSpeed = get(CAMERA_LOOK_SPEED);
        this.moveSpeed = get(CAMERA_MOVE_SPEED);
        this.strafeSpeed = moveSpeed / (float) Math.sqrt(2);

        this.movement = new Vector2f();
    }

    @Override
    public void update(double delta) {
        float translateX = 0;
        float translateZ = 0;

        float multiplier;
        if (movement.x != 0 && movement.y != 0) {
            multiplier = strafeSpeed;
        } else {
            multiplier = moveSpeed;
        }

        if (movement.x < 0) {
            translateX += Math.sin(Math.toRadians(rotation.x - 90));
            translateZ -= Math.cos(Math.toRadians(rotation.x - 90));
        } else if (movement.x > 0) {
            translateX -= Math.sin(Math.toRadians(rotation.x - 90));
            translateZ += Math.cos(Math.toRadians(rotation.x - 90));
        }

        if (movement.y < 0) {
            translateX += Math.sin(Math.toRadians(rotation.x));
            translateZ -= Math.cos(Math.toRadians(rotation.x));
        } else if (movement.y > 0) {
            translateX -= Math.sin(Math.toRadians(rotation.x));
            translateZ += Math.cos(Math.toRadians(rotation.x));
        }

        position.add(translateX * (float) delta * multiplier, 0, translateZ * (float) delta * multiplier);

        super.update(delta);
    }

    public void moveLeft() {
        moveHorizontal(-1);
    }

    public void moveRight() {
        moveHorizontal(1);
    }

    public void moveForward() {
        moveVertical(-1);
    }

    public void moveBackward() {
        moveVertical(1);
    }

    private void moveHorizontal(float direction) {
        movement.x += movement.x == direction ? 0 : direction;
    }

    private void moveVertical(float direction) {
        movement.y += movement.y == direction ? 0 : direction;
    }

    public void lookAt(Vector2f lookAt) {
        rotation.add(lookAt.x * lookSpeed, -lookAt.y * lookSpeed, 0.0f);

        clampRotation();

        if (rotation.y > lookUpLimit) {
            rotation.y = lookUpLimit;
        } else if (rotation.y < lookDownLimit) {
            rotation.y = lookDownLimit;
        }
    }

}
