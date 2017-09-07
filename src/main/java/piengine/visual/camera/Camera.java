package piengine.visual.camera;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import piengine.core.base.api.Updatable;
import piengine.core.base.exception.PIEngineException;
import piengine.object.entity.domain.Entity;

import static piengine.core.property.domain.ApplicationProperties.get;
import static piengine.core.property.domain.PropertyKeys.CAMERA_FAR_PLANE;
import static piengine.core.property.domain.PropertyKeys.CAMERA_FOV;
import static piengine.core.property.domain.PropertyKeys.CAMERA_LOOK_DOWN_LIMIT;
import static piengine.core.property.domain.PropertyKeys.CAMERA_LOOK_SPEED;
import static piengine.core.property.domain.PropertyKeys.CAMERA_LOOK_UP_LIMIT;
import static piengine.core.property.domain.PropertyKeys.CAMERA_MOVE_SPEED;
import static piengine.core.property.domain.PropertyKeys.CAMERA_NEAR_PLANE;
import static piengine.core.property.domain.PropertyKeys.CAMERA_VIEW_PORT_HEIGHT;
import static piengine.core.property.domain.PropertyKeys.CAMERA_VIEW_PORT_WIDTH;
import static piengine.core.utils.MatrixUtils.IDENTITY_MATRIX;
import static piengine.core.utils.MatrixUtils.ORTHOGRAPHIC_PROJECTION_MATRIX;
import static piengine.core.utils.MatrixUtils.PERSPECTIVE_PROJECTION_MATRIX;
import static piengine.core.utils.MatrixUtils.VIEW_MATRIX;

public class Camera extends Entity implements Updatable {

    public final Matrix4f projection;
    public final Matrix4f view;

    private final Vector2f viewPort;
    private final float fieldOfView;
    private final float nearPlane;
    private final float farPlane;
    private final float lookUpLimit;
    private final float lookDownLimit;
    private final float lookSpeed;
    private final float moveSpeed;
    private final float strafeSpeed;

    private final Vector2f movement;

    public Camera(final Entity parent) {
        super(parent);

        this.viewPort = new Vector2f(get(CAMERA_VIEW_PORT_WIDTH), get(CAMERA_VIEW_PORT_HEIGHT));
        this.fieldOfView = get(CAMERA_FOV);
        this.nearPlane = get(CAMERA_NEAR_PLANE);
        this.farPlane = get(CAMERA_FAR_PLANE);
        this.lookDownLimit = get(CAMERA_LOOK_DOWN_LIMIT);
        this.lookUpLimit = get(CAMERA_LOOK_UP_LIMIT);
        this.lookSpeed = get(CAMERA_LOOK_SPEED);
        this.moveSpeed = get(CAMERA_MOVE_SPEED);
        this.strafeSpeed = moveSpeed / (float) Math.sqrt(2);

        this.projection = setProjectionMatrix(ProjectionType.PERSPECTIVE);
        this.view = IDENTITY_MATRIX();

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

        VIEW_MATRIX(position, rotation).mul(VIEW_MATRIX(parent.getPosition(), parent.getRotation()), this.view);
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

    private Matrix4f setProjectionMatrix(ProjectionType projectionType) {
        switch (projectionType) {
            case PERSPECTIVE:
                return PERSPECTIVE_PROJECTION_MATRIX(viewPort, fieldOfView, nearPlane, farPlane);
            case ORTHOGRAPHIC:
                return ORTHOGRAPHIC_PROJECTION_MATRIX(viewPort, nearPlane, farPlane);
            default:
                throw new PIEngineException("Invalid projection type %s!", projectionType.name());
        }
    }

    private void clampRotation() {
        if (rotation.x > 360) {
            rotation.sub(360, 0, 0);
        } else if (rotation.x < 0) {
            rotation.add(360, 0, 0);
        }
    }

}
