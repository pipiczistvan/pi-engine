package piengine.core.domain.assets.camera;

import piengine.core.input.manager.InputManager;
import piengine.visual.camera.asset.CameraAsset;
import piengine.visual.camera.domain.FirstPersonCamera;
import piengine.visual.window.manager.WindowManager;
import puppeteer.annotation.premade.Wire;

public class FirstPersonCameraAsset extends CameraAsset<FirstPersonCamera> {

    @Wire
    public FirstPersonCameraAsset(final InputManager inputManager, final WindowManager windowManager) {
        super(inputManager, windowManager);
    }
}
