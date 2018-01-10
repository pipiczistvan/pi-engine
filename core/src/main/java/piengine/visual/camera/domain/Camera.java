package piengine.visual.camera.domain;

import org.joml.Matrix4f;
import org.joml.Vector2i;
import piengine.core.base.exception.PIEngineException;
import piengine.object.entity.domain.Entity;

import static piengine.core.utils.MatrixUtils.IDENTITY_MATRIX;
import static piengine.core.utils.MatrixUtils.ORTHOGRAPHIC_PROJECTION_MATRIX;
import static piengine.core.utils.MatrixUtils.PERSPECTIVE_PROJECTION_MATRIX;

public abstract class Camera extends Entity {

    private final Matrix4f projection;
    public final Matrix4f view;//todo: temporary
    public final Vector2i viewport;
    public final CameraAttribute attribute;
    private final ProjectionType projectionType;

    Camera(final Entity parent, final Vector2i viewport, final CameraAttribute attribute, final ProjectionType projectionType) {
        super(parent);

        this.viewport = viewport;
        this.attribute = attribute;
        this.projectionType = projectionType;
        this.projection = setProjectionMatrix(projectionType);
        this.view = IDENTITY_MATRIX();
    }

    public Matrix4f getProjection() {
        return projection;
    }

    public Matrix4f getView() {
        calculateViewMatrix(view);
        return view;
    }

    public void recalculateProjection() {
        this.projection.set(setProjectionMatrix(projectionType));
    }

    public void recalculateView() {
        calculateViewMatrix(view);
    }

    protected abstract void calculateViewMatrix(final Matrix4f viewMatrix);

    private Matrix4f setProjectionMatrix(final ProjectionType projectionType) {
        switch (projectionType) {
            case PERSPECTIVE:
                return PERSPECTIVE_PROJECTION_MATRIX(viewport, attribute.fieldOfView, attribute.nearPlane, attribute.farPlane);
            case ORTHOGRAPHIC:
                return ORTHOGRAPHIC_PROJECTION_MATRIX(viewport, attribute.nearPlane, attribute.farPlane);
            default:
                throw new PIEngineException("Invalid projection type %s!", projectionType.name());
        }
    }
}
