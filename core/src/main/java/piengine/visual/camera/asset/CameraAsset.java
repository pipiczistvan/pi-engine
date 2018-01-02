package piengine.visual.camera.asset;

import org.joml.Vector2f;
import piengine.core.input.manager.InputManager;
import piengine.object.asset.domain.Asset;
import piengine.visual.camera.domain.Camera;
import piengine.visual.render.domain.RenderPlan;
import piengine.visual.render.manager.RenderManager;
import piengine.visual.window.manager.WindowManager;

import static org.lwjgl.glfw.GLFW.*;
import static piengine.core.input.domain.KeyEventType.PRESS;
import static piengine.core.input.domain.KeyEventType.RELEASE;
import static piengine.visual.render.domain.RenderPlan.createPlan;

public abstract class CameraAsset<C extends Camera> extends Asset {

    public final C camera;
    public boolean movingEnabled = true;
    public boolean lookingEnabled = true;

    private final InputManager inputManager;
    private final WindowManager windowManager;

    public CameraAsset(final RenderManager renderManager, final InputManager inputManager, final WindowManager windowManager) {
        super(renderManager);

        this.camera = getCamera();
        this.inputManager = inputManager;
        this.windowManager = windowManager;
    }

    @Override
    public void initialize() {
        inputManager.addEvent(GLFW_KEY_A, PRESS, () -> move(MoveDirection.LEFT));
        inputManager.addEvent(GLFW_KEY_S, PRESS, () -> move(MoveDirection.BACKWARD));
        inputManager.addEvent(GLFW_KEY_D, PRESS, () -> move(MoveDirection.RIGHT));
        inputManager.addEvent(GLFW_KEY_W, PRESS, () -> move(MoveDirection.FORWARD));
        inputManager.addEvent(GLFW_KEY_A, RELEASE, () -> move(MoveDirection.RIGHT));
        inputManager.addEvent(GLFW_KEY_S, RELEASE, () -> move(MoveDirection.FORWARD));
        inputManager.addEvent(GLFW_KEY_D, RELEASE, () -> move(MoveDirection.LEFT));
        inputManager.addEvent(GLFW_KEY_W, RELEASE, () -> move(MoveDirection.BACKWARD));
        inputManager.addEvent(v -> {
            if (lookingEnabled) {
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
        camera.update(delta);
    }

    protected abstract C getCamera();

    @Override
    protected RenderPlan createRenderPlan() {
        return createPlan();
    }

    private void move(final MoveDirection direction) {
        if (movingEnabled) {
            switch (direction) {
                case LEFT:
                    camera.moveLeft();
                    break;
                case RIGHT:
                    camera.moveRight();
                    break;
                case FORWARD:
                    camera.moveForward();
                    break;
                case BACKWARD:
                    camera.moveBackward();
                    break;
            }
        }
    }

    private enum MoveDirection {
        LEFT,
        RIGHT,
        FORWARD,
        BACKWARD
    }
}
