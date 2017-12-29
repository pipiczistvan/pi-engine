package piengine.core.domain;

import org.joml.Vector4f;
import piengine.core.architecture.scene.domain.Scene;
import piengine.core.domain.assets.camera.MovingCameraAsset;
import piengine.core.domain.assets.camera.StaticCameraAsset;
import piengine.core.domain.assets.object.CubeAsset;
import piengine.core.domain.assets.object.LightAsset;
import piengine.core.domain.assets.object.SquareAsset;
import piengine.core.input.manager.InputManager;
import piengine.object.asset.manager.AssetManager;
import piengine.visual.framebuffer.domain.FrameBuffer;
import piengine.visual.render.domain.ScenePlan;
import piengine.visual.render.manager.RenderManager;
import piengine.visual.window.manager.WindowManager;
import puppeteer.annotation.premade.Wire;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static piengine.core.input.domain.KeyEventType.PRESS;
import static piengine.visual.render.domain.RenderType.RENDER_PLANE_MODEL;
import static piengine.visual.render.domain.RenderType.RENDER_SOLID_MODEL;
import static piengine.visual.render.domain.ScenePlan.createPlan;

public class InitScene extends Scene {

    private final InputManager inputManager;
    private final WindowManager windowManager;

    private MovingCameraAsset movingCameraAsset;
    private StaticCameraAsset staticCameraAsset;
    private LightAsset lightAsset;
    private CubeAsset cubeAsset;
    private SquareAsset squareAsset;
    private FrameBuffer frameBuffer;

    @Wire
    public InitScene(final RenderManager renderManager, final AssetManager assetManager,
                     final InputManager inputManager, final WindowManager windowManager) {
        super(renderManager, assetManager);

        this.inputManager = inputManager;
        this.windowManager = windowManager;
    }

    @Override
    public void initialize() {
        super.initialize();

        frameBuffer = squareAsset.getFrameBuffer();
        inputManager.addEvent(GLFW_KEY_ESCAPE, PRESS, windowManager::closeWindow);
    }

    @Override
    protected void createAssets() {
        movingCameraAsset = createAsset(MovingCameraAsset.class);
        staticCameraAsset = createAsset(StaticCameraAsset.class);

        lightAsset = createAsset(LightAsset.class);

        cubeAsset = createAsset(CubeAsset.class);
        squareAsset = createAsset(SquareAsset.class);
    }

    @Override
    protected void initializeAssets() {
        movingCameraAsset.setPosition(0, 0, 5);
        lightAsset.setPosition(5, 5, 5);
    }

    @Override
    protected ScenePlan createRenderPlan() {
        return createPlan()
                .withClearColor(new Vector4f(0))
                .doClearScreen()

                .doBindFrameBuffer(frameBuffer)
                .doClearScreen()

                .withAsset(movingCameraAsset)
                .withAsset(lightAsset)
                .withAsset(cubeAsset)
                .doRender(RENDER_SOLID_MODEL)

                .doUnbindFrameBuffer()

                .withAsset(staticCameraAsset)
                .withAsset(squareAsset)
                .doRender(RENDER_PLANE_MODEL);
    }

}
