package piengine.visual.camera.domain;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector2i;
import piengine.core.base.api.Updatable;
import piengine.core.base.exception.PIEngineException;
import piengine.object.entity.domain.Entity;

import static piengine.core.utils.MatrixUtils.*;

public abstract class Camera extends Entity implements Updatable {

    public final Matrix4f projection;
    public final Matrix4f view;
    public final Vector2i viewport;

    private final float fieldOfView;
    private final float nearPlane;
    private final float farPlane;
    private final float lookUpLimit;
    private final float lookDownLimit;
    private final float lookSpeed;
    private final float moveSpeed;
    private final float strafeSpeed;

    private final Vector2f movement;

    public Camera(final Entity parent, final Vector2i viewport, final float fieldOfView,
                  final float nearPlane, final float farPlane, final float lookUpLimit,
                  final float lookDownLimit, final float lookSpeed, final float moveSpeed,
                  final ProjectionType projectionType) {
        super(parent);

        this.viewport = viewport;
        this.fieldOfView = fieldOfView;
        this.nearPlane = nearPlane;
        this.farPlane = farPlane;
        this.lookUpLimit = lookUpLimit;
        this.lookDownLimit = lookDownLimit;
        this.lookSpeed = lookSpeed;
        this.moveSpeed = moveSpeed;
        this.strafeSpeed = this.moveSpeed / (float) Math.sqrt(2);

        this.movement = new Vector2f();

        this.projection = setProjectionMatrix(projectionType);
        this.view = IDENTITY_MATRIX();
    }

    @Override
    public void update(final double delta) {
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

        calculateViewMatrix(this.view);
    }

    public void lookAt(final Vector2f lookAt) {
        rotation.add(lookAt.x * lookSpeed, -lookAt.y * lookSpeed, 0.0f);

        clampRotation();

        if (rotation.y > lookUpLimit) {
            rotation.y = lookUpLimit;
        } else if (rotation.y < lookDownLimit) {
            rotation.y = lookDownLimit;
        }
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

    private void moveHorizontal(final float direction) {
        movement.x += movement.x == direction ? 0 : direction;
    }

    private void moveVertical(final float direction) {
        movement.y += movement.y == direction ? 0 : direction;
    }

    protected abstract void calculateViewMatrix(final Matrix4f viewMatrix);


    protected void clampRotation() {
        if (rotation.x > 360) {
            rotation.sub(360, 0, 0);
        } else if (rotation.x < 0) {
            rotation.add(360, 0, 0);
        }
    }

    private Matrix4f setProjectionMatrix(final ProjectionType projectionType) {
        switch (projectionType) {
            case PERSPECTIVE:
                return PERSPECTIVE_PROJECTION_MATRIX(viewport, fieldOfView, nearPlane, farPlane);
            case ORTHOGRAPHIC:
                return ORTHOGRAPHIC_PROJECTION_MATRIX(viewport, farPlane);
            default:
                throw new PIEngineException("Invalid projection type %s!", projectionType.name());
        }
    }

}
