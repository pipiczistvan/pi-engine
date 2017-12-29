package piengine.visual.camera;

import org.joml.Vector2i;
import piengine.object.entity.domain.Entity;
import piengine.visual.framebuffer.domain.FrameBuffer;

public class StaticCamera extends Camera {

    public StaticCamera(Entity parent, FrameBuffer frameBuffer, Vector2i viewport, float fieldOfView, float nearPlane, float farPlane, ProjectionType projectionType) {
        super(parent, frameBuffer, viewport, fieldOfView, nearPlane, farPlane, projectionType);
    }

}
