package piengine.core.domain;

import org.joml.Vector2i;
import org.joml.Vector3f;
import piengine.core.architecture.scene.domain.Scene;
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
import piengine.visual.fog.Fog;
import piengine.visual.framebuffer.domain.Framebuffer;
import piengine.visual.framebuffer.manager.FramebufferManager;
import piengine.visual.render.domain.plan.RenderPlan;
import piengine.visual.render.domain.plan.RenderPlanBuilder;
import piengine.visual.render.manager.RenderManager;
import piengine.visual.window.manager.WindowManager;
import puppeteer.annotation.premade.Wire;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT_CONTROL;
import static piengine.core.base.type.property.ApplicationProperties.get;
import static piengine.core.base.type.property.PropertyKeys.CAMERA_FAR_PLANE;
import static piengine.core.base.type.property.PropertyKeys.CAMERA_FOV;
import static piengine.core.base.type.property.PropertyKeys.CAMERA_LOOK_DOWN_LIMIT;
import static piengine.core.base.type.property.PropertyKeys.CAMERA_LOOK_SPEED;
import static piengine.core.base.type.property.PropertyKeys.CAMERA_LOOK_UP_LIMIT;
import static piengine.core.base.type.property.PropertyKeys.CAMERA_MOVE_SPEED;
import static piengine.core.base.type.property.PropertyKeys.CAMERA_NEAR_PLANE;
import static piengine.core.base.type.property.PropertyKeys.CAMERA_VIEWPORT_HEIGHT;
import static piengine.core.base.type.property.PropertyKeys.CAMERA_VIEWPORT_WIDTH;
import static piengine.core.input.domain.KeyEventType.PRESS;
import static piengine.object.camera.domain.ProjectionType.PERSPECTIVE;
import static piengine.visual.framebuffer.domain.FramebufferAttachment.COLOR_ATTACHMENT;
import static piengine.visual.framebuffer.domain.FramebufferAttachment.RENDER_BUFFER_ATTACHMENT;
import static piengine.visual.postprocessing.domain.EffectType.HIGH_CONTRAST_EFFECT;

public class InitScene extends Scene {

    private static final Vector2i VIEWPORT = new Vector2i(get(CAMERA_VIEWPORT_WIDTH), get(CAMERA_VIEWPORT_HEIGHT));

    private final InputManager inputManager;
    private final WindowManager windowManager;
    private final FramebufferManager framebufferManager;
    private final SkyboxManager skyboxManager;
    private final CanvasManager canvasManager;
    private final TerrainManager terrainManager;

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
                     final InputManager inputManager, final WindowManager windowManager,
                     final FramebufferManager framebufferManager, final SkyboxManager skyboxManager,
                     final CanvasManager canvasManager, final TerrainManager terrainManager) {
        super(renderManager, assetManager);

        this.inputManager = inputManager;
        this.windowManager = windowManager;
        this.framebufferManager = framebufferManager;
        this.skyboxManager = skyboxManager;
        this.canvasManager = canvasManager;
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
        terrain = terrainManager.supply(new Vector3f(-128, 0, -128), new Vector3f(256, 10, 256), "heightmap2");

        cameraAsset = createAsset(CameraAsset.class, new CameraAssetArgument(
                terrain,
                get(CAMERA_LOOK_UP_LIMIT),
                get(CAMERA_LOOK_DOWN_LIMIT),
                get(CAMERA_LOOK_SPEED),
                get(CAMERA_MOVE_SPEED)));

        camera = new FirstPersonCamera(cameraAsset, VIEWPORT, new CameraAttribute(get(CAMERA_FOV), get(CAMERA_NEAR_PLANE), get(CAMERA_FAR_PLANE)), PERSPECTIVE);

        mapAsset = createAsset(MapAsset.class, new MapAssetArgument(VIEWPORT, camera, terrain));

        lampAsset = createAsset(LampAsset.class, new LampAssetArgument());

        framebuffer = framebufferManager.supply(VIEWPORT, COLOR_ATTACHMENT, RENDER_BUFFER_ATTACHMENT);
        mainCanvas = canvasManager.supply(this, framebuffer, HIGH_CONTRAST_EFFECT);

        buttonAsset = createAsset(ButtonAsset.class, new ButtonAssetArgument(
                "buttonDefault", "buttonHover", "buttonPress",
                VIEWPORT, "Please press me!", () -> System.out.println("Button clicked!")));
        fpsAsset = createAsset(FpsAsset.class, new FpsAssetArgument());

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

        super.update(delta);
    }

    @Override
    protected RenderPlan createRenderPlan() {
        return RenderPlanBuilder
                .createPlan(VIEWPORT)
                .bindFrameBuffer(
                        framebuffer,
                        RenderPlanBuilder
                                .createPlan(camera, fog, skybox)
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
}
