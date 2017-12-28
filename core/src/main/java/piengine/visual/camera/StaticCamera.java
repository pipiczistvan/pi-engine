package piengine.visual.camera;

import org.joml.Vector2i;
import piengine.object.entity.domain.Entity;

public class StaticCamera extends Camera {

    public StaticCamera(Entity parent, Vector2i viewport, float fieldOfView, float nearPlane, float farPlane, ProjectionType projectionType) {
        super(parent, viewport, fieldOfView, nearPlane, farPlane, projectionType);
    }

}
