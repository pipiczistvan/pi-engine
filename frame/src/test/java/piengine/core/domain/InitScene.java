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
import piengine.object.terrain.domain.TerrainKey;
import piengine.object.terrain.manager.TerrainManager;
import piengine.visual.camera.asset.CameraAssetArgument;
import piengine.visual.framebuffer.domain.FrameBuffer;
import piengine.visual.framebuffer.domain.FrameBufferData;
import piengine.visual.framebuffer.manager.FrameBufferManager;
import piengine.visual.light.Light;
import piengine.visual.render.domain.RenderPlan;
import piengine.visual.render.manager.RenderManager;
import piengine.visual.window.manager.WindowManager;
import puppeteer.annotation.premade.Wire;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT_CONTROL;
import static piengine.core.input.domain.KeyEventType.PRESS;
import static piengine.core.property.domain.ApplicationProperties.get;
import static piengine.core.property.domain.PropertyKeys.CAMERA_LOOK_DOWN_LIMIT;
import static piengine.core.property.domain.PropertyKeys.CAMERA_LOOK_SPEED;
import static piengine.core.property.domain.PropertyKeys.CAMERA_LOOK_UP_LIMIT;
import static piengine.core.property.domain.PropertyKeys.CAMERA_MOVE_SPEED;
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
    private FirstPersonCameraAsset cameraAsset;
    private Light light;
    private Terrain terrain;

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
        inputManager.addEvent(GLFW_KEY_RIGHT_CONTROL, PRESS, () -> cameraAsset.lookingEnabled = !cameraAsset.lookingEnabled);
    }

    @Override
    protected void createAssets() {
        frameBuffer = frameBufferManager.supply(new FrameBufferData(VIEWPORT));
        terrain = terrainManager.supply(new TerrainKey(this, "heightmap"));
        cameraAsset = createAsset(FirstPersonCameraAsset.class, new CameraAssetArgument(
                terrain,
                get(CAMERA_LOOK_UP_LIMIT),
                get(CAMERA_LOOK_DOWN_LIMIT),
                get(CAMERA_LOOK_SPEED),
                get(CAMERA_MOVE_SPEED)));
        light = new Light(this);

        squareAsset = createAsset(SquareAsset.class, new SquareAssetArgument(VIEWPORT, frameBuffer));
        cubeAsset = createAsset(CubeAsset.class, new CubeAssetArgument(cameraAsset, light));
        buttonAsset = createAsset(ButtonAsset.class, new ButtonAssetArgument(
                "buttonDefault", "buttonHover", "buttonPress",
                VIEWPORT, "Please press me!", () -> System.out.println("Button clicked!")));
    }

    @Override
    protected void initializeAssets() {
        light.setPosition(100, 100, 100);
        terrain.setPosition(-64, -0.5f,-64);

        buttonAsset.setPosition(-0.75f, 0.875f, 0);

        cameraAsset.setPosition(0, 0, 0);
    }

    @Override
    protected RenderPlan createRenderPlan() {
        return createPlan()
                .renderToFrameBuffer(frameBuffer,
                        createPlan()
                                .clearScreen(CLEAR_COLOR_BLACK)
                                .renderTerrain(cameraAsset, light, terrain)
                                .loadAsset(cubeAsset)
                )
                .clearScreen(CLEAR_COLOR_BLACK)
                .loadAsset(squareAsset)
                .loadAsset(buttonAsset);
    }
}
