package piengine.core.domain;

import org.joml.Vector2i;
import org.joml.Vector4f;
import piengine.core.architecture.scene.domain.Scene;
import piengine.core.domain.assets.camera.FirstPersonCameraAsset;
import piengine.core.domain.assets.object.cube.CubeAsset;
import piengine.core.domain.assets.object.cube.CubeAssetArgument;
import piengine.core.domain.assets.object.square.SquareAsset;
import piengine.core.domain.assets.object.square.SquareAssetArgument;
import piengine.core.input.manager.InputManager;
import piengine.gui.asset.ButtonAsset;
import piengine.gui.asset.ButtonAssetArgument;
import piengine.object.asset.manager.AssetManager;
import piengine.object.terrain.domain.Terrain;
import piengine.object.terrain.manager.TerrainManager;
import piengine.visual.camera.asset.CameraAsset;
import piengine.visual.framebuffer.domain.FrameBuffer;
import piengine.visual.framebuffer.domain.FrameBufferData;
import piengine.visual.framebuffer.manager.FrameBufferManager;
import piengine.visual.light.Light;
import piengine.visual.render.domain.RenderPlan;
import piengine.visual.render.manager.RenderManager;
import piengine.visual.window.manager.WindowManager;
import puppeteer.annotation.premade.Wire;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static piengine.core.input.domain.KeyEventType.PRESS;
import static piengine.visual.render.domain.RenderPlan.createPlan;

public class InitScene extends Scene {

    private static final Vector4f CLEAR_COLOR_BLACK = new Vector4f();
    private static final Vector4f CLEAR_COLOR_GREEN = new Vector4f(0, 1, 0, 1);
    private static final Vector2i VIEWPORT = new Vector2i(800, 600);

    private final InputManager inputManager;
    private final WindowManager windowManager;
    private final FrameBufferManager frameBufferManager;
    private final TerrainManager terrainManager;

    private FrameBuffer frameBuffer;
    private CameraAsset cameraAsset;
    private Light light;
    private Terrain terrain1;
    private Terrain terrain2;

    private SquareAsset squareAsset;
    private CubeAsset cubeAsset;
    private ButtonAsset buttonAsset;

    @Wire
    public InitScene(final RenderManager renderManager, final AssetManager assetManager,
                     final InputManager inputManager, final WindowManager windowManager,
                     final FrameBufferManager frameBufferManager, final TerrainManager terrainManager) {
        super(renderManager, assetManager);

        this.inputManager = inputManager;
        this.windowManager = windowManager;
        this.frameBufferManager = frameBufferManager;
        this.terrainManager = terrainManager;
    }

    @Override
    public void initialize() {
        super.initialize();
        inputManager.addEvent(GLFW_KEY_ESCAPE, PRESS, windowManager::closeWindow);
        inputManager.addEvent(GLFW_KEY_SPACE, PRESS, () -> cameraAsset.lookingEnabled = !cameraAsset.lookingEnabled);
    }

    @Override
    protected void createAssets() {
        frameBuffer = frameBufferManager.supply(new FrameBufferData(VIEWPORT));
        cameraAsset = createAsset(FirstPersonCameraAsset.class);
        light = new Light(this);
        terrain1 = terrainManager.supply("a");
        terrain2 = terrainManager.supply("b");

        squareAsset = createAsset(SquareAsset.class, new SquareAssetArgument(VIEWPORT, frameBuffer));
        cubeAsset = createAsset(CubeAsset.class, new CubeAssetArgument(cameraAsset, light));
        buttonAsset = createAsset(ButtonAsset.class, new ButtonAssetArgument(
                "buttonDefault", "buttonHover", "buttonPress",
                VIEWPORT, "Please press me!", () -> System.out.println("Button clicked!")));
    }

    @Override
    protected void initializeAssets() {
        light.setPosition(5, 5, 5);
        terrain1.setPosition(0, -1, -64);
        terrain2.setPosition(-128, -1, -64);

        buttonAsset.setPosition(-0.75f, 0.875f, 0);

        cameraAsset.setPosition(0, 0, 5);
    }

    @Override
    protected RenderPlan createRenderPlan() {
        return createPlan()
                .renderToFrameBuffer(frameBuffer,
                        createPlan()
                                .clearScreen(CLEAR_COLOR_GREEN)
                                .renderTerrain(cameraAsset, light, terrain1, terrain2)
                                .loadAsset(cubeAsset)
                )
                .clearScreen(CLEAR_COLOR_BLACK)
                .loadAsset(squareAsset)
                .loadAsset(buttonAsset);
    }
}
