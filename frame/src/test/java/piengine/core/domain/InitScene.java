package piengine.core.domain;

import org.joml.Vector2i;
import org.joml.Vector4f;
import piengine.core.architecture.scene.domain.Scene;
import piengine.core.domain.assets.camera.MovingCameraAsset;
import piengine.core.domain.assets.object.CubeAsset;
import piengine.core.domain.assets.object.LightAsset;
import piengine.core.domain.assets.object.square.SquareAsset;
import piengine.core.input.manager.InputManager;
import piengine.gui.asset.ButtonAsset;
import piengine.object.asset.manager.AssetManager;
import piengine.visual.framebuffer.domain.FrameBuffer;
import piengine.visual.framebuffer.domain.FrameBufferData;
import piengine.visual.framebuffer.manager.FrameBufferManager;
import piengine.visual.render.domain.ScenePlan;
import piengine.visual.render.manager.RenderManager;
import piengine.visual.window.manager.WindowManager;
import puppeteer.annotation.premade.Wire;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static piengine.core.input.domain.KeyEventType.PRESS;
import static piengine.visual.render.domain.RenderType.RENDER_SOLID_MODEL;
import static piengine.visual.render.domain.ScenePlan.createPlan;

public class InitScene extends Scene {

    private final InputManager inputManager;
    private final WindowManager windowManager;
    private final FrameBufferManager frameBufferManager;

    private MovingCameraAsset movingCameraAsset;
    private LightAsset lightAsset;
    private CubeAsset cubeAsset;
    private SquareAsset squareAsset;
    private ButtonAsset buttonAsset;
    private FrameBuffer frameBuffer;

    @Wire
    public InitScene(final RenderManager renderManager, final AssetManager assetManager,
                     final InputManager inputManager, final WindowManager windowManager,
                     final FrameBufferManager frameBufferManager) {
        super(renderManager, assetManager);

        this.inputManager = inputManager;
        this.windowManager = windowManager;
        this.frameBufferManager = frameBufferManager;
    }

    @Override
    public void initialize() {
        super.initialize();
        inputManager.addEvent(GLFW_KEY_ESCAPE, PRESS, windowManager::closeWindow);
    }

    @Override
    protected void createAssets() {
        frameBuffer = frameBufferManager.supply(new FrameBufferData(new Vector2i(800, 600)));

//        movingCameraAsset = createAsset(MovingCameraAsset.class);

        lightAsset = createAsset(LightAsset.class);

        cubeAsset = createAsset(CubeAsset.class);
        squareAsset = createAsset(SquareAsset.class, SquareAsset.createArguments(frameBuffer));

        buttonAsset = createAsset(ButtonAsset.class, ButtonAsset.createArguments(
                "buttonDefault", "buttonHover", "buttonPress",
                new Vector2i(800, 600), () -> System.out.println("OK")));
    }

    @Override
    protected void initializeAssets() {
//        movingCameraAsset.setPosition(0, 0, 5);
        lightAsset.setPosition(5, 5, 5);

        buttonAsset.setPosition(0.25f, 0f, 0f);
    }

    @Override
    protected ScenePlan createRenderPlan() {
        return createPlan()
//                .doBindFrameBuffer(frameBuffer)
//                .withScenePlan(worldPlan())
//                .doUnbindFrameBuffer()

                .withScenePlan(guiPlan());
    }

    private ScenePlan worldPlan() {
        return createPlan()
                .withScenePlan(preparePlan())

                .withAsset(movingCameraAsset)
                .withAsset(lightAsset)
                .withAsset(cubeAsset)

                .doRender(RENDER_SOLID_MODEL);
    }

    private ScenePlan guiPlan() {
        return createPlan()
//                .withScenePlan(preparePlan())
//                .withAsset(squareAsset)
//                .doRender(RENDER_PLANE_MODEL)

                .withScenePlan(preparePlan())
                .withAsset(buttonAsset);
    }

    private ScenePlan preparePlan() {
        return createPlan()
                .withClearColor(new Vector4f(1))
                .withViewport(new Vector2i(800, 600))
                .doClearScreen();
    }
}
