package piengine.visual.camera.domain;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector3f;
import piengine.core.base.api.Updatable;
import piengine.core.base.exception.PIEngineException;
import piengine.object.entity.domain.Entity;

import static piengine.core.utils.MatrixUtils.IDENTITY_MATRIX;
import static piengine.core.utils.MatrixUtils.ORTHOGRAPHIC_PROJECTION_MATRIX;
import static piengine.core.utils.MatrixUtils.PERSPECTIVE_PROJECTION_MATRIX;

public abstract class Camera extends Entity implements Updatable {

    public final Matrix4f projection;
    public final Matrix4f view;
    public final Vector2i viewport;
    private final CameraAttribute attribute;

    private final Vector2f movement;

    Camera(final Entity parent, final Vector2i viewport, final CameraAttribute attribute, final ProjectionType projectionType) {
        super(parent);

        this.viewport = viewport;
        this.attribute = attribute;
        this.projection = setProjectionMatrix(projectionType);
        this.view = IDENTITY_MATRIX();

        this.movement = new Vector2f();
    }

    @Override
    public void update(final double delta) {
        float translateX = 0;
        float translateZ = 0;

        float multiplier;
        if (movement.x != 0 && movement.y != 0) {
            multiplier = attribute.strafeSpeed;
        } else {
            multiplier = attribute.moveSpeed;
        }

        Vector3f rotation = getRotation();

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

        addPosition(translateX * (float) delta * multiplier, 0, translateZ * (float) delta * multiplier);

        calculateViewMatrix(this.view);
    }

    public void lookAt(final Vector2f lookAt) {
        addRotation(lookAt.x * attribute.lookSpeed, -lookAt.y * attribute.lookSpeed, 0.0f);

        Vector3f rotation = getRotation();

        clampYaw(rotation.x);
        clampPitch(rotation);
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

    private void clampYaw(final float yaw) {
        if (yaw > 360) {
            addRotation(-360, 0, 0);
        } else if (yaw < 0) {
            addRotation(360, 0, 0);
        }
    }

    private void clampPitch(final Vector3f rotation) {
        if (rotation.y > attribute.lookUpLimit) {
            setRotation(rotation.x, attribute.lookUpLimit, rotation.z);
        } else if (rotation.y < attribute.lookDownLimit) {
            setRotation(rotation.x, attribute.lookDownLimit, rotation.z);
        }
    }

    private Matrix4f setProjectionMatrix(final ProjectionType projectionType) {
        switch (projectionType) {
            case PERSPECTIVE:
                return PERSPECTIVE_PROJECTION_MATRIX(viewport, attribute.fieldOfView, attribute.nearPlane, attribute.farPlane);
            case ORTHOGRAPHIC:
                return ORTHOGRAPHIC_PROJECTION_MATRIX(viewport, attribute.farPlane);
            default:
                throw new PIEngineException("Invalid projection type %s!", projectionType.name());
        }
    }
}
