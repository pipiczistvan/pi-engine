package piengine.core.domain;

import org.joml.Vector2i;
import piengine.core.architecture.scene.domain.Scene;
import piengine.core.domain.assets.camera.FirstPersonCameraAsset;
import piengine.core.domain.assets.object.cube.CubeAsset;
import piengine.core.domain.assets.object.cube.CubeAssetArgument;
import piengine.core.domain.assets.object.square.SquareAsset;
import piengine.core.domain.assets.object.square.SquareAssetArgument;
import piengine.core.input.manager.InputManager;
import piengine.core.time.manager.TimeManager;
import piengine.core.utils.ColorUtils;
import piengine.gui.asset.ButtonAsset;
import piengine.gui.asset.ButtonAssetArgument;
import piengine.object.asset.manager.AssetManager;
import piengine.object.terrain.domain.Terrain;
import piengine.object.terrain.domain.TerrainKey;
import piengine.object.terrain.manager.TerrainManager;
import piengine.object.water.domain.Water;
import piengine.object.water.domain.WaterKey;
import piengine.object.water.manager.WaterManager;
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
import static piengine.core.base.type.property.ApplicationProperties.get;
import static piengine.core.base.type.property.PropertyKeys.CAMERA_LOOK_DOWN_LIMIT;
import static piengine.core.base.type.property.PropertyKeys.CAMERA_LOOK_SPEED;
import static piengine.core.base.type.property.PropertyKeys.CAMERA_LOOK_UP_LIMIT;
import static piengine.core.base.type.property.PropertyKeys.CAMERA_MOVE_SPEED;
import static piengine.core.base.type.property.PropertyKeys.CAMERA_VIEWPORT_HEIGHT;
import static piengine.core.base.type.property.PropertyKeys.CAMERA_VIEWPORT_WIDTH;
import static piengine.core.input.domain.KeyEventType.PRESS;
import static piengine.visual.framebuffer.domain.FrameBufferAttachment.COLOR_TEXTURE_ATTACHMENT;
import static piengine.visual.render.domain.RenderPlan.createPlan;

public class InitScene extends Scene {

    private static final Vector2i VIEWPORT = new Vector2i(get(CAMERA_VIEWPORT_WIDTH), get(CAMERA_VIEWPORT_HEIGHT));

    private final InputManager inputManager;
    private final WindowManager windowManager;
    private final FrameBufferManager frameBufferManager;
    private final TerrainManager terrainManager;
    private final WaterManager waterManager;
    private final TimeManager timeManager;

    private FrameBuffer frameBuffer;
    private FirstPersonCameraAsset cameraAsset;
    private Light light;
    private Terrain terrain;
    private Water water;

    private SquareAsset squareAsset;
    private CubeAsset cubeAsset;
    private ButtonAsset buttonAsset;

    @Wire
    public InitScene(final RenderManager renderManager, final AssetManager assetManager,
                     final InputManager inputManager, final WindowManager windowManager,
                     final FrameBufferManager frameBufferManager, final TerrainManager terrainManager,
                     final WaterManager waterManager, final TimeManager timeManager) {
        super(renderManager, assetManager);

        this.inputManager = inputManager;
        this.windowManager = windowManager;
        this.frameBufferManager = frameBufferManager;
        this.terrainManager = terrainManager;
        this.waterManager = waterManager;
        this.timeManager = timeManager;
    }

    @Override
    public void initialize() {
        super.initialize();
        inputManager.addEvent(GLFW_KEY_ESCAPE, PRESS, windowManager::closeWindow);
        inputManager.addEvent(GLFW_KEY_RIGHT_CONTROL, PRESS, () -> cameraAsset.lookingEnabled = !cameraAsset.lookingEnabled);
    }

    @Override
    protected void createAssets() {
        frameBuffer = frameBufferManager.supply(new FrameBufferData(VIEWPORT, COLOR_TEXTURE_ATTACHMENT));
        terrain = terrainManager.supply(new TerrainKey(this, "heightmap"));
        water = waterManager.supply(new WaterKey(this));
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
        light.setPosition(0, 20, 0);
        cubeAsset.setPosition(0, 20, 0);

        terrain.setPosition(-64, 0, -64);
        terrain.setScale(128, 15, 128);

        water.setScale(128, 0, 128);
        water.setPosition(-64, -4.9f, -64);

        buttonAsset.setPosition(-0.75f, 0.875f, 0);

        cameraAsset.setPosition(0, 0, 0);
    }

    @Override
    public void update(double delta) {
//        light.addPosition((float) (1f * delta), 0, 0);
//        cubeAsset.addPosition((float) (1f * delta), 0, 0);

        System.out.println(timeManager.getFPS());

        super.update(delta);
    }

    @Override
    protected RenderPlan createRenderPlan() {
        return createPlan()
                .renderToFrameBuffer(
                        frameBuffer,
                        null,
                        createPlan()
                                .renderWater(
                                        cameraAsset,
                                        water,
                                        frameBuffer,
                                        createPlan()
                                                .clearScreen(ColorUtils.BLACK)
                                                .renderTerrain(cameraAsset, light, terrain)
                                                .loadAsset(cubeAsset)
                                )
                )
                .clearScreen(ColorUtils.BLACK)
                .loadAsset(squareAsset)
                .loadAsset(buttonAsset);
    }
}
