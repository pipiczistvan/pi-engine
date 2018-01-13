package piengine.core.domain.assets.camera;

import piengine.core.input.manager.InputManager;
import piengine.visual.camera.asset.CameraAsset;
import piengine.visual.window.manager.WindowManager;
import puppeteer.annotation.premade.Wire;

public class ThirdPersonCameraAsset extends CameraAsset {

    @Wire
    public ThirdPersonCameraAsset(final InputManager inputManager, final WindowManager windowManager) {
        super(inputManager, windowManager);
    }
}
