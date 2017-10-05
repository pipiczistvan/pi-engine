package piengine.object.asset.domain;

import org.joml.Vector2f;
import piengine.core.input.manager.InputManager;
import piengine.visual.camera.LookingCamera;
import piengine.visual.render.domain.AssetPlan;
import piengine.visual.render.manager.RenderManager;
import piengine.visual.window.manager.WindowManager;
import puppeteer.annotation.premade.Wire;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;
import static piengine.core.input.domain.KeyEventType.PRESS;
import static piengine.core.input.domain.KeyEventType.RELEASE;
import static piengine.visual.render.domain.AssetPlan.createPlan;

public class ObserverCamera extends Asset {

    private final InputManager inputManager;
    private final WindowManager windowManager;
    private final LookingCamera camera;
    private final Vector2f lastCursorPos;

    private boolean lookingEnabled;

    @Wire
    public ObserverCamera(final RenderManager renderManager,
                          final InputManager inputManager,
                          final WindowManager windowManager) {
        super(renderManager);

        this.inputManager = inputManager;
        this.windowManager = windowManager;
        this.camera = new LookingCamera(this);
        this.lastCursorPos = new Vector2f();
    }

    @Override
    public void initialize() {
        camera.setPosition(0, 0, 5f);

        inputManager.addEvent(GLFW_MOUSE_BUTTON_LEFT, PRESS, () -> {
            lastCursorPos.set(windowManager.getPointer());
            this.enableLooking();
        });
        inputManager.addEvent(GLFW_MOUSE_BUTTON_LEFT, RELEASE, this::disableLooking);

        inputManager.addEvent(v -> {
            if (lookingEnabled) {
                v.sub(lastCursorPos, lastCursorPos);
                camera.lookAt(lastCursorPos);
                lastCursorPos.set(v);
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

    private void enableLooking() {
        this.lookingEnabled = true;
    }

    private void disableLooking() {
        this.lookingEnabled = false;
    }

}
