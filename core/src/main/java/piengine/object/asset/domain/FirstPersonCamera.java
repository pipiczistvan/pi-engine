package piengine.object.asset.domain;

import org.joml.Vector2f;
import piengine.core.input.manager.InputManager;
import piengine.visual.camera.MovingCamera;
import piengine.visual.render.domain.AssetPlan;
import piengine.visual.render.manager.RenderManager;
import piengine.visual.window.manager.WindowManager;
import puppeteer.annotation.premade.Wire;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;
import static piengine.core.input.domain.KeyEventType.PRESS;
import static piengine.core.input.domain.KeyEventType.RELEASE;
import static piengine.visual.render.domain.AssetPlan.createPlan;

public class FirstPersonCamera extends Asset {

    private final InputManager inputManager;
    private final WindowManager windowManager;
    private final MovingCamera camera;

    @Wire
    public FirstPersonCamera(final RenderManager renderManager,
                             final InputManager inputManager,
                             final WindowManager windowManager) {
        super(renderManager);

        this.inputManager = inputManager;
        this.windowManager = windowManager;
        this.camera = new MovingCamera(this);
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
            Vector2f delta = new Vector2f();
            Vector2f windowCenter = windowManager.getWindowCenter();
            v.sub(windowCenter, delta);
            if (Math.abs(delta.x) >= 1 || Math.abs(delta.y) >= 1) {
                camera.lookAt(delta);
                windowManager.setPointer(windowCenter);
            }
        });
    }

    @Override
    public void update(double delta) {
        camera.update(delta);
    }

    @Override
    protected AssetPlan createRenderPlan() {
        return createPlan()
                .withCamera(camera);
    }

}
