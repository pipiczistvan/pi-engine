package piengine.core.domain.assets.camera;

import org.joml.Vector2i;
import piengine.core.input.manager.InputManager;
import piengine.visual.camera.asset.CameraAsset;
import piengine.visual.camera.domain.CameraAttribute;
import piengine.visual.camera.domain.FirstPersonCamera;
import piengine.visual.window.manager.WindowManager;
import puppeteer.annotation.premade.Wire;

import static piengine.core.base.type.property.ApplicationProperties.get;
import static piengine.core.base.type.property.PropertyKeys.CAMERA_FAR_PLANE;
import static piengine.core.base.type.property.PropertyKeys.CAMERA_FOV;
import static piengine.core.base.type.property.PropertyKeys.CAMERA_NEAR_PLANE;
import static piengine.core.base.type.property.PropertyKeys.CAMERA_VIEWPORT_HEIGHT;
import static piengine.core.base.type.property.PropertyKeys.CAMERA_VIEWPORT_WIDTH;
import static piengine.visual.camera.domain.ProjectionType.PERSPECTIVE;

public class FirstPersonCameraAsset extends CameraAsset<FirstPersonCamera> {

    @Wire
    public FirstPersonCameraAsset(final InputManager inputManager,
                                  final WindowManager windowManager) {
        super(inputManager, windowManager);
    }

    @Override
    protected FirstPersonCamera getCamera() {
        Vector2i viewport = new Vector2i(get(CAMERA_VIEWPORT_WIDTH), get(CAMERA_VIEWPORT_HEIGHT));
        CameraAttribute attribute = new CameraAttribute(
                get(CAMERA_FOV),
                get(CAMERA_NEAR_PLANE),
                get(CAMERA_FAR_PLANE)
        );

        return new FirstPersonCamera(this, viewport, attribute, PERSPECTIVE);
    }
}
