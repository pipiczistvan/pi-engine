package piengine.core.domain.assets.camera;

import org.joml.Vector2f;
import org.joml.Vector2i;
import piengine.core.input.manager.InputManager;
import piengine.object.asset.domain.Asset;
import piengine.visual.camera.LookingCamera;
import piengine.visual.framebuffer.domain.FrameBuffer;
import piengine.visual.framebuffer.domain.FrameBufferData;
import piengine.visual.framebuffer.manager.FrameBufferManager;
import piengine.visual.render.domain.AssetPlan;
import piengine.visual.render.manager.RenderManager;
import piengine.visual.window.manager.WindowManager;
import puppeteer.annotation.premade.Wire;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;
import static piengine.core.input.domain.KeyEventType.PRESS;
import static piengine.core.input.domain.KeyEventType.RELEASE;
import static piengine.core.property.domain.ApplicationProperties.get;
import static piengine.core.property.domain.PropertyKeys.*;
import static piengine.visual.camera.ProjectionType.ORTHOGRAPHIC;
import static piengine.visual.render.domain.AssetPlan.createPlan;

public class LookingCameraAsset extends Asset {

    private final InputManager inputManager;
    private final WindowManager windowManager;
    private final LookingCamera camera;
    private final Vector2f lastCursorPos;

    private boolean lookingEnabled;

    @Wire
    public LookingCameraAsset(final RenderManager renderManager,
                              final InputManager inputManager,
                              final WindowManager windowManager) {
        super(renderManager);

        this.inputManager = inputManager;
        this.windowManager = windowManager;
        this.lastCursorPos = new Vector2f();

        Vector2i viewport = new Vector2i(get(CAMERA_VIEWPORT_WIDTH), get(CAMERA_VIEWPORT_HEIGHT));

        this.camera = new LookingCamera(this,
                viewport,
                get(CAMERA_FOV),
                get(CAMERA_NEAR_PLANE),
                get(CAMERA_FAR_PLANE),
                ORTHOGRAPHIC);
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
