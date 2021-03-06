package piengine.core.domain;

import org.joml.Vector2i;
import org.joml.Vector3f;
import piengine.core.architecture.scene.domain.Scene;
import piengine.core.base.type.color.Color;
import piengine.core.domain.assets.object.fps.FpsAsset;
import piengine.core.domain.assets.object.fps.FpsAssetArgument;
import piengine.core.domain.assets.object.lamp.LampAsset;
import piengine.core.domain.assets.object.lamp.LampAssetArgument;
import piengine.core.domain.assets.object.map.MapAsset;
import piengine.core.domain.assets.object.map.MapAssetArgument;
import piengine.core.input.manager.InputManager;
import piengine.core.utils.ColorUtils;
import piengine.gui.asset.ButtonAsset;
import piengine.gui.asset.ButtonAssetArgument;
import piengine.object.asset.manager.AssetManager;
import piengine.object.asset.plan.GuiRenderAssetContextBuilder;
import piengine.object.camera.asset.CameraAsset;
import piengine.object.camera.asset.CameraAssetArgument;
import piengine.object.camera.domain.Camera;
import piengine.object.camera.domain.CameraAttribute;
import piengine.object.camera.domain.FirstPersonCamera;
import piengine.object.canvas.domain.Canvas;
import piengine.object.canvas.manager.CanvasManager;
import piengine.object.skybox.domain.Skybox;
import piengine.object.skybox.manager.SkyboxManager;
import piengine.object.terrain.domain.Terrain;
import piengine.object.terrain.manager.TerrainManager;
import piengine.visual.display.manager.DisplayManager;
import piengine.visual.fog.Fog;
import piengine.visual.framebuffer.domain.Framebuffer;
import piengine.visual.framebuffer.manager.FramebufferManager;
import piengine.visual.render.domain.plan.GuiRenderPlanBuilder;
import piengine.visual.render.domain.plan.RenderPlan;
import piengine.visual.render.domain.plan.WorldRenderPlanBuilder;
import piengine.visual.render.manager.RenderManager;
import puppeteer.annotation.premade.Wire;

import static piengine.core.base.type.property.ApplicationProperties.get;
import static piengine.core.base.type.property.PropertyKeys.CAMERA_FAR_PLANE;
import static piengine.core.base.type.property.PropertyKeys.CAMERA_FOV;
import static piengine.core.base.type.property.PropertyKeys.CAMERA_LOOK_DOWN_LIMIT;
import static piengine.core.base.type.property.PropertyKeys.CAMERA_LOOK_SPEED;
import static piengine.core.base.type.property.PropertyKeys.CAMERA_LOOK_UP_LIMIT;
import static piengine.core.base.type.property.PropertyKeys.CAMERA_MOVE_SPEED;
import static piengine.core.base.type.property.PropertyKeys.CAMERA_NEAR_PLANE;
import static piengine.core.input.domain.Key.KEY_ESCAPE;
import static piengine.core.input.domain.Key.KEY_RIGHT_CONTROL;
import static piengine.core.input.domain.KeyEventType.PRESS;
import static piengine.object.camera.domain.ProjectionType.PERSPECTIVE;
import static piengine.visual.framebuffer.domain.FramebufferAttachment.COLOR_BUFFER_MULTISAMPLE_ATTACHMENT;
import static piengine.visual.framebuffer.domain.FramebufferAttachment.DEPTH_BUFFER_MULTISAMPLE_ATTACHMENT;
import static piengine.visual.postprocessing.domain.EffectType.ANTIALIAS_EFFECT;

public class InitScene extends Scene {

    public static final int TERRAIN_SCALE = 256;
    public static final int WATER_SCALE = TERRAIN_SCALE / 2;
    private static final Color[] BIOME_COLORS = {
            new Color(0.78823529412f, 0.69803921569f, 0.38823529412f),
            new Color(0.52941176471f, 0.72156862745f, 0.32156862745f),
            new Color(0.3137254902f, 0.67058823529f, 0.36470588235f),
            new Color(0.47058823529f, 0.47058823529f, 0.47058823529f),
            new Color(0.78431372549f, 0.78431372549f, 0.82352941176f)
    };

    private final InputManager inputManager;
    private final DisplayManager displayManager;
    private final FramebufferManager framebufferManager;
    private final SkyboxManager skyboxManager;
    private final CanvasManager canvasManager;
    private final TerrainManager terrainManager;

    private Vector2i viewport;
    private Framebuffer framebuffer;
    private Fog fog;
    private Skybox skybox;
    private Terrain terrain;
    private Canvas mainCanvas;
    private Camera camera;

    private CameraAsset cameraAsset;
    private LampAsset lampAsset;
    private MapAsset mapAsset;
    private ButtonAsset buttonAsset;
    private FpsAsset fpsAsset;

    @Wire
    public InitScene(final RenderManager renderManager, final AssetManager assetManager,
                     final InputManager inputManager, final DisplayManager displayManager,
                     final FramebufferManager framebufferManager, final SkyboxManager skyboxManager,
                     final CanvasManager canvasManager, final TerrainManager terrainManager) {
        super(renderManager, assetManager);

        this.inputManager = inputManager;
        this.displayManager = displayManager;
        this.framebufferManager = framebufferManager;
        this.skyboxManager = skyboxManager;
        this.canvasManager = canvasManager;
        this.terrainManager = terrainManager;
    }

    @Override
    public void initialize() {
        viewport = displayManager.getViewport();
        super.initialize();
        inputManager.addKeyEvent(KEY_ESCAPE, PRESS, displayManager::closeDisplay);
        inputManager.addKeyEvent(KEY_RIGHT_CONTROL, PRESS, () -> {
            cameraAsset.lookingEnabled = !cameraAsset.lookingEnabled;
            displayManager.setCursorVisibility(!cameraAsset.lookingEnabled);
        });
    }

    @Override
    public void createAssets() {
        terrain = terrainManager.supply(new Vector3f(-TERRAIN_SCALE / 2, 0, -TERRAIN_SCALE / 2), new Vector3f(TERRAIN_SCALE, 10, TERRAIN_SCALE), "heightmap2", BIOME_COLORS);

        cameraAsset = createAsset(CameraAsset.class, new CameraAssetArgument(
                terrain,
                get(CAMERA_LOOK_UP_LIMIT),
                get(CAMERA_LOOK_DOWN_LIMIT),
                get(CAMERA_LOOK_SPEED),
                get(CAMERA_MOVE_SPEED)));
        cameraAsset.setPosition(-2, 0, 0);

        camera = new FirstPersonCamera(cameraAsset, viewport, new CameraAttribute(get(CAMERA_FOV), get(CAMERA_NEAR_PLANE), get(CAMERA_FAR_PLANE)), PERSPECTIVE);

        fog = new Fog(ColorUtils.BLACK, 0.015f, 1.5f);

        skybox = skyboxManager.supply(150f,
                "skybox/nightRight", "skybox/nightLeft", "skybox/nightTop",
                "skybox/nightBottom", "skybox/nightBack", "skybox/nightFront");

        mapAsset = createAsset(MapAsset.class, new MapAssetArgument(viewport, camera, terrain));

        lampAsset = createAsset(LampAsset.class, new LampAssetArgument());

        framebuffer = framebufferManager.supply(viewport, false, COLOR_BUFFER_MULTISAMPLE_ATTACHMENT, DEPTH_BUFFER_MULTISAMPLE_ATTACHMENT);
        mainCanvas = canvasManager.supply(this, framebuffer, ANTIALIAS_EFFECT);

        buttonAsset = createAsset(ButtonAsset.class, new ButtonAssetArgument(
                "buttonDefault", "buttonHover", "buttonPress",
                viewport, "Please press me!", () -> System.out.println("Button clicked!")));
        fpsAsset = createAsset(FpsAsset.class, new FpsAssetArgument(viewport));

        skybox = skyboxManager.supply(150f,
                "skybox/nightRight", "skybox/nightLeft", "skybox/nightTop",
                "skybox/nightBottom", "skybox/nightBack", "skybox/nightFront");

        fog = new Fog(ColorUtils.BLACK, 0.015f, 1.5f);
    }

    @Override
    protected void initializeAssets() {
        buttonAsset.setPosition(-0.75f, 0.875f, 0);

        cameraAsset.setPosition(-2, 0, 0);

        float lampX = 0;
        float lampZ = 0;
        float lampY = terrain.getHeight(lampX, lampZ);
        lampAsset.setPosition(lampX, lampY, lampZ);
    }

    @Override
    public void update(final float delta) {
        skybox.addRotation(0.5f * delta);
    }

    @Override
    protected RenderPlan createRenderPlan() {
        return GuiRenderPlanBuilder
                .createPlan(viewport)
                .bindFrameBuffer(
                        framebuffer,
                        WorldRenderPlanBuilder
                                .createPlan(camera)
                                .loadAssets(mapAsset)
                                .clearScreen(ColorUtils.BLACK)
                                .render()
                )
                .loadAssetContext(GuiRenderAssetContextBuilder
                        .create()
                        .loadCanvases(mainCanvas)
                        .build()
                )
                .loadAssets(fpsAsset)
                .clearScreen(ColorUtils.BLACK)
                .render();
    }

    @Override
    public void resize(final int oldWidth, final int oldHeight, final int width, final int height) {
        viewport.set(width, height);
        camera.recalculateProjection();
    }
}
