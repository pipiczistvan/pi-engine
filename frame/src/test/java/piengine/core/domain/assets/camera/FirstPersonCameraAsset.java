package piengine.core.domain.assets.camera;

import org.joml.Vector2f;
import org.joml.Vector2i;
import piengine.core.input.manager.InputManager;
import piengine.visual.camera.asset.CameraAsset;
import piengine.visual.camera.domain.FirstPersonCamera;
import piengine.visual.render.manager.RenderManager;
import piengine.visual.window.manager.WindowManager;
import puppeteer.annotation.premade.Wire;

import static org.lwjgl.glfw.GLFW.*;
import static piengine.core.input.domain.KeyEventType.PRESS;
import static piengine.core.input.domain.KeyEventType.RELEASE;
import static piengine.core.property.domain.ApplicationProperties.get;
import static piengine.core.property.domain.PropertyKeys.*;
import static piengine.visual.camera.domain.ProjectionType.PERSPECTIVE;

public class FirstPersonCameraAsset extends CameraAsset<FirstPersonCamera> {

    public boolean active = true;

    private final InputManager inputManager;
    private final WindowManager windowManager;

    @Wire
    public FirstPersonCameraAsset(final RenderManager renderManager,
                                  final InputManager inputManager,
                                  final WindowManager windowManager) {
        super(renderManager);

        this.inputManager = inputManager;
        this.windowManager = windowManager;
    }

    @Override
    public void initialize() {
        inputManager.addEvent(GLFW_KEY_A, PRESS, camera::moveLeft);
        inputManager.addEvent(GLFW_KEY_S, PRESS, camera::moveBackward);
        inputManager.addEvent(GLFW_KEY_D, PRESS, camera::moveRight);
        inputManager.addEvent(GLFW_KEY_W, PRESS, camera::moveForward);
        inputManager.addEvent(GLFW_KEY_A, RELEASE, camera::moveRight);
        inputManager.addEvent(GLFW_KEY_S, RELEASE, camera::moveForward);
        inputManager.addEvent(GLFW_KEY_D, RELEASE, camera::moveLeft);
        inputManager.addEvent(GLFW_KEY_W, RELEASE, camera::moveBackward);
        inputManager.addEvent(v -> {
            if (active) {
                Vector2f delta = new Vector2f();
                Vector2f windowCenter = windowManager.getWindowCenter();
                v.sub(windowCenter, delta);
                if (Math.abs(delta.x) >= 1 || Math.abs(delta.y) >= 1) {
                    camera.lookAt(delta);
                    windowManager.setPointer(windowCenter);
                }
            }
        });
    }

    @Override
    public void update(double delta) {
        if (active) {
            camera.update(delta);
        }
    }

    @Override
    protected FirstPersonCamera getCamera() {
        Vector2i viewport = new Vector2i(get(CAMERA_VIEWPORT_WIDTH), get(CAMERA_VIEWPORT_HEIGHT));

        return new FirstPersonCamera(this,
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
