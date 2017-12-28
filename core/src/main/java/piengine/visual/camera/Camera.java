package piengine.visual.camera;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector2i;
import piengine.core.base.api.Updatable;
import piengine.core.base.exception.PIEngineException;
import piengine.object.entity.domain.Entity;

import static piengine.core.utils.MatrixUtils.IDENTITY_MATRIX;
import static piengine.core.utils.MatrixUtils.ORTHOGRAPHIC_PROJECTION_MATRIX;
import static piengine.core.utils.MatrixUtils.PERSPECTIVE_PROJECTION_MATRIX;
import static piengine.core.utils.MatrixUtils.VIEW_MATRIX;

public abstract class Camera extends Entity implements Updatable {

    public final Matrix4f projection;
    public final Matrix4f view;
    public final Vector2i viewport;

    private final float fieldOfView;
    private final float nearPlane;
    private final float farPlane;

    public Camera(Entity parent, Vector2i viewport, float fieldOfView, float nearPlane, float farPlane, ProjectionType projectionType) {
        super(parent);

        this.viewport = viewport;
        this.fieldOfView = fieldOfView;
        this.nearPlane = nearPlane;
        this.farPlane = farPlane;

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
                return PERSPECTIVE_PROJECTION_MATRIX(viewport, fieldOfView, nearPlane, farPlane);
            case ORTHOGRAPHIC:
                return ORTHOGRAPHIC_PROJECTION_MATRIX(viewport, farPlane);
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
