package piengine.visual.camera;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import piengine.core.base.api.Updatable;
import piengine.core.base.exception.PIEngineException;
import piengine.object.entity.domain.Entity;

import static piengine.core.property.domain.ApplicationProperties.get;
import static piengine.core.property.domain.PropertyKeys.CAMERA_FAR_PLANE;
import static piengine.core.property.domain.PropertyKeys.CAMERA_FOV;
import static piengine.core.property.domain.PropertyKeys.CAMERA_NEAR_PLANE;
import static piengine.core.property.domain.PropertyKeys.CAMERA_VIEW_PORT_HEIGHT;
import static piengine.core.property.domain.PropertyKeys.CAMERA_VIEW_PORT_WIDTH;
import static piengine.core.utils.MatrixUtils.IDENTITY_MATRIX;
import static piengine.core.utils.MatrixUtils.ORTHOGRAPHIC_PROJECTION_MATRIX;
import static piengine.core.utils.MatrixUtils.PERSPECTIVE_PROJECTION_MATRIX;
import static piengine.core.utils.MatrixUtils.VIEW_MATRIX;

public abstract class Camera extends Entity implements Updatable {

    public final Matrix4f projection;
    public final Matrix4f view;

    private final Vector2f viewPort;
    private final float fieldOfView;
    private final float nearPlane;
    private final float farPlane;

    public Camera(final Entity parent, final ProjectionType projectionType) {
        super(parent);

        this.viewPort = new Vector2f(get(CAMERA_VIEW_PORT_WIDTH), get(CAMERA_VIEW_PORT_HEIGHT));
        this.fieldOfView = get(CAMERA_FOV);
        this.nearPlane = get(CAMERA_NEAR_PLANE);
        this.farPlane = get(CAMERA_FAR_PLANE);

        this.projection = setProjectionMatrix(projectionType);
        this.view = IDENTITY_MATRIX();
    }

    @Override
    public void update(double delta) {
        VIEW_MATRIX(position, rotation).mul(VIEW_MATRIX(parent.getPosition(), parent.getRotation()), this.view);
    }

    private Matrix4f setProjectionMatrix(ProjectionType projectionType) {
        switch (projectionType) {
            case PERSPECTIVE:
                return PERSPECTIVE_PROJECTION_MATRIX(viewPort, fieldOfView, nearPlane, farPlane);
            case ORTHOGRAPHIC:
                return ORTHOGRAPHIC_PROJECTION_MATRIX(viewPort, farPlane);
            default:
                throw new PIEngineException("Invalid projection type %s!", projectionType.name());
        }
    }

    protected void clampRotation() {
        if (rotation.x > 360) {
            rotation.sub(360, 0, 0);
        } else if (rotation.x < 0) {
            rotation.add(360, 0, 0);
        }
    }

}
