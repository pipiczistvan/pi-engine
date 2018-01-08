package piengine.core.domain;

import org.joml.Vector2i;
import piengine.core.architecture.scene.domain.Scene;
import piengine.core.domain.assets.camera.FirstPersonCameraAsset;
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
import piengine.visual.image.domain.Image;
import piengine.visual.image.manager.ImageManager;
import piengine.visual.light.Light;
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
    private final ImageManager imageManager;

    private FrameBuffer frameBuffer;
    private FirstPersonCameraAsset cameraAsset;
    private Light light1;
    private Light light2;
    private Light light3;
    private Light light4;
    private Fog fog;
    private Terrain terrain;
    private Water water;
    private Model cube;
    private Model[] trees = new Model[40];
    private Image treeTexture;
    private Model lamp;
    private Image lampTexture;
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
                     final TextManager textManager, final ImageManager imageManager) {
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
        this.imageManager = imageManager;
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
        light1 = new Light(this);
        light2 = new Light(this);
        light3 = new Light(this);
        light4 = new Light(this);
        lampTexture = imageManager.supply("lamp");
        lamp = modelManager.supply("lamp", this, lampTexture);

        fog = new Fog(ColorUtils.BLACK, 0.015f, 1.5f);

        cube = modelManager.supply("cube", this, ColorUtils.RED);

        treeTexture = imageManager.supply("lowPolyTree");
        for (int i = 0; i < trees.length; i++) {
            trees[i] = modelManager.supply("lowPolyTree", this, treeTexture);
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
        light1.setColor(0.9568627451f, 0.96862745098f, 0.67843137255f);
        light1.setPosition(0, 4, 0);
        light1.setAttenuation(1, 0.01f, 0.002f);

        light2.setColor(0, 1, 0);
        light2.setPosition(-200, 20, 0);
        light3.setColor(0, 0, 1);
        light3.setPosition(0, 20, 200);
        light4.setColor(1, 1, 1);
        light4.setPosition(0, 20, -200);

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
        lamp.setScale(0.5f);
        lamp.setPosition(lampX, lampY, lampZ);
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
                                .loadLights(light1)
                                .loadModels(cube)
                                .loadModels(trees)
                                .loadModels(lamp)
                                .loadTerrains(terrain)
                                .loadWaters(water)
                                .clearScreen(ColorUtils.BLACK)
                                .render()
                )
                .loadModels(squareAsset.getModels())
                .loadModels(buttonAsset.getModels())
                .loadTexts(buttonAsset.getTexts())
                .loadTexts(fpsText)
                .clearScreen(ColorUtils.BLACK)
                .render();
    }
}
