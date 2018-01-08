package piengine.core.domain;

import org.joml.Vector2i;
import piengine.core.architecture.scene.domain.Scene;
import piengine.core.domain.assets.camera.FirstPersonCameraAsset;
import piengine.core.domain.assets.object.lamp.LampAsset;
import piengine.core.domain.assets.object.lamp.LampAssetArgument;
import piengine.core.domain.assets.object.square.SquareAsset;
import piengine.core.domain.assets.object.square.SquareAssetArgument;
import piengine.core.input.manager.InputManager;
import piengine.core.time.manager.TimeManager;
import piengine.core.utils.ColorUtils;
import piengine.gui.asset.ButtonAsset;
import piengine.gui.asset.ButtonAssetArgument;
import piengine.object.asset.manager.AssetManager;
import piengine.object.model.domain.Model;
import piengine.object.model.manager.ModelManager;
import piengine.object.terrain.domain.Terrain;
import piengine.object.terrain.domain.TerrainKey;
import piengine.object.terrain.manager.TerrainManager;
import piengine.object.water.domain.Water;
import piengine.object.water.domain.WaterKey;
import piengine.object.water.manager.WaterManager;
import piengine.visual.camera.asset.CameraAssetArgument;
import piengine.visual.fog.Fog;
import piengine.visual.framebuffer.domain.FrameBuffer;
import piengine.visual.framebuffer.domain.FrameBufferData;
import piengine.visual.framebuffer.manager.FrameBufferManager;
import piengine.visual.render.domain.plan.RenderPlan;
import piengine.visual.render.domain.plan.RenderPlanBuilder;
import piengine.visual.render.manager.RenderManager;
import piengine.visual.window.manager.WindowManager;
import piengine.visual.writing.font.domain.Font;
import piengine.visual.writing.font.manager.FontManager;
import piengine.visual.writing.text.domain.Text;
import piengine.visual.writing.text.domain.TextConfiguration;
import piengine.visual.writing.text.manager.TextManager;
import puppeteer.annotation.premade.Wire;

import java.util.Random;

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
import static piengine.visual.framebuffer.domain.FrameBufferAttachment.COLOR_ATTACHMENT;
import static piengine.visual.framebuffer.domain.FrameBufferAttachment.RENDER_BUFFER_ATTACHMENT;

public class InitScene extends Scene {

    private static final float WAVE_SPEED = 0.2f;
    private static final Vector2i VIEWPORT = new Vector2i(get(CAMERA_VIEWPORT_WIDTH), get(CAMERA_VIEWPORT_HEIGHT));

    private final InputManager inputManager;
    private final WindowManager windowManager;
    private final FrameBufferManager frameBufferManager;
    private final TerrainManager terrainManager;
    private final WaterManager waterManager;
    private final ModelManager modelManager;
    private final TimeManager timeManager;
    private final FontManager fontManager;
    private final TextManager textManager;

    private FrameBuffer frameBuffer;
    private FirstPersonCameraAsset cameraAsset;
    private LampAsset lampAsset;
    private Fog fog;
    private Terrain terrain;
    private Water water;

    private Model cube;

    private Model[] trees = new Model[40];

    private Font font;
    private Text fpsText;

    private SquareAsset squareAsset;
    private ButtonAsset buttonAsset;

    @Wire
    public InitScene(final RenderManager renderManager, final AssetManager assetManager,
                     final InputManager inputManager, final WindowManager windowManager,
                     final FrameBufferManager frameBufferManager, final TerrainManager terrainManager,
                     final WaterManager waterManager, final ModelManager modelManager,
                     final TimeManager timeManager, final FontManager fontManager,
                     final TextManager textManager) {
        super(renderManager, assetManager);

        this.inputManager = inputManager;
        this.windowManager = windowManager;
        this.frameBufferManager = frameBufferManager;
        this.terrainManager = terrainManager;
        this.waterManager = waterManager;
        this.modelManager = modelManager;
        this.timeManager = timeManager;
        this.fontManager = fontManager;
        this.textManager = textManager;
    }

    @Override
    public void initialize() {
        super.initialize();
        inputManager.addEvent(GLFW_KEY_ESCAPE, PRESS, windowManager::closeWindow);
        inputManager.addEvent(GLFW_KEY_RIGHT_CONTROL, PRESS, () -> cameraAsset.lookingEnabled = !cameraAsset.lookingEnabled);
    }

    @Override
    protected void createAssets() {
        frameBuffer = frameBufferManager.supply(new FrameBufferData(VIEWPORT, COLOR_ATTACHMENT, RENDER_BUFFER_ATTACHMENT));
        terrain = terrainManager.supply(new TerrainKey(this, "heightmap2"));
        water = waterManager.supply(new WaterKey(this, VIEWPORT, new Vector2i(128, 128)));
        cameraAsset = createAsset(FirstPersonCameraAsset.class, new CameraAssetArgument(
                terrain,
                get(CAMERA_LOOK_UP_LIMIT),
                get(CAMERA_LOOK_DOWN_LIMIT),
                get(CAMERA_LOOK_SPEED),
                get(CAMERA_MOVE_SPEED)));
        lampAsset = createAsset(LampAsset.class, new LampAssetArgument());

        fog = new Fog(ColorUtils.BLACK, 0.015f, 1.5f);

        cube = modelManager.supply("cube", this, ColorUtils.RED);

        for (int i = 0; i < trees.length; i++) {
            trees[i] = modelManager.supply("lowPolyTree", this, "lowPolyTree");
        }

        squareAsset = createAsset(SquareAsset.class, new SquareAssetArgument(VIEWPORT, frameBuffer));
        buttonAsset = createAsset(ButtonAsset.class, new ButtonAssetArgument(
                "buttonDefault", "buttonHover", "buttonPress",
                VIEWPORT, "Please press me!", () -> System.out.println("Button clicked!")));

        font = fontManager.supply("candara");
        fpsText = textManager.supply(TextConfiguration.textConfig().withFont(font), this);
    }

    @Override
    protected void initializeAssets() {
        cube.setPosition(4, 0f, -14);

        terrain.setPosition(-64, 0, -64);
        terrain.setScale(128, 5, 128);

        water.setScale(128, 0, 128);
        water.setPosition(-64, -2.0f, -64);

        buttonAsset.setPosition(-0.75f, 0.875f, 0);

        fpsText.setPosition(0.85f, 0.85f, 0);

        cameraAsset.setPosition(-2, 0, 0);

        Random random = new Random();
        float waterHeight = water.getPosition().y;
        for (Model tree : trees) {
            float x;
            float y;
            float z;
            do {
                x = random.nextFloat() * 128 - 64;
                z = random.nextFloat() * 128 - 64;
                y = terrain.getHeight(x, z) - 0.1f;
            } while (y < waterHeight - 0.2);
            float scale = random.nextFloat() * 0.05f + 0.1f;

            tree.setScale(scale);
            tree.setPosition(x, y, z);
        }

        float lampX = 0;
        float lampZ = 0;
        float lampY = terrain.getHeight(lampX, lampZ);
        lampAsset.setPosition(lampX, lampY, lampZ);
    }

    @Override
    public void update(double delta) {
        water.waveFactor += WAVE_SPEED * delta;

        cube.addRotation((float) (5f * delta), (float) (10f * delta), (float) (15f * delta));

        textManager.update(fpsText, TextConfiguration.textConfig().withFont(font).withFontSize(2).withText("FPS: " + timeManager.getFPS()));

        super.update(delta);
    }

    @Override
    protected RenderPlan createRenderPlan() {
        return RenderPlanBuilder
                .createPlan(VIEWPORT)
                .bindFrameBuffer(
                        frameBuffer,
                        RenderPlanBuilder
                                .createPlan(cameraAsset.camera, fog)
                                .loadLights(lampAsset.getLights())
                                .loadModels(lampAsset.getModels())
                                .loadModels(cube)
                                .loadModels(trees)
                                .loadTerrains(terrain)
                                .loadWaters(water)
                                .clearScreen(ColorUtils.BLACK)
                                .render()
                )
                .loadModels(squareAsset.getModels())
//                .loadModels(buttonAsset.getModels())
//                .loadTexts(buttonAsset.getTexts())
                .loadTexts(fpsText)
                .clearScreen(ColorUtils.BLACK)
                .render();
    }
}
