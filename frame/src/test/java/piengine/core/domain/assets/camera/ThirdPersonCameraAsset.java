package piengine.core.domain.assets.camera;

import org.joml.Vector2i;
import piengine.core.input.manager.InputManager;
import piengine.visual.camera.asset.CameraAsset;
import piengine.visual.camera.domain.ThirdPersonCamera;
import piengine.visual.render.manager.RenderManager;
import piengine.visual.window.manager.WindowManager;
import puppeteer.annotation.premade.Wire;

import static piengine.core.property.domain.ApplicationProperties.get;
import static piengine.core.property.domain.PropertyKeys.*;
import static piengine.visual.camera.domain.ProjectionType.PERSPECTIVE;

public class ThirdPersonCameraAsset extends CameraAsset<ThirdPersonCamera> {

    @Wire
    public ThirdPersonCameraAsset(final RenderManager renderManager,
                                  final InputManager inputManager,
                                  final WindowManager windowManager) {
        super(renderManager, inputManager, windowManager);
    }

    @Override
    protected ThirdPersonCamera getCamera() {
        Vector2i viewport = new Vector2i(get(CAMERA_VIEWPORT_WIDTH), get(CAMERA_VIEWPORT_HEIGHT));

        return new ThirdPersonCamera(this,
                viewport,
                get(CAMERA_FOV),
                get(CAMERA_NEAR_PLANE),
                get(CAMERA_FAR_PLANE),
                get(CAMERA_LOOK_UP_LIMIT),
                get(CAMERA_LOOK_DOWN_LIMIT),
                get(CAMERA_LOOK_SPEED),
                get(CAMERA_MOVE_SPEED),
                PERSPECTIVE);
    }
}
