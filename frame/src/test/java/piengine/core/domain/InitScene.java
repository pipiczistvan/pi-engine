package piengine.core.domain;

import org.joml.Vector2i;
import piengine.core.architecture.scene.domain.Scene;
import piengine.core.domain.assets.camera.FirstPersonCameraAsset;
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
import piengine.object.canvas.domain.Canvas;
import piengine.object.canvas.domain.CanvasKey;
import piengine.object.canvas.manager.CanvasManager;
import piengine.visual.camera.asset.CameraAssetArgument;
import piengine.visual.camera.domain.CameraAttribute;
import piengine.visual.camera.domain.FirstPersonCamera;
import piengine.visual.cubemap.domain.CubeMap;
import piengine.visual.cubemap.domain.CubeMapKey;
import piengine.visual.cubemap.manager.CubeMapManager;
import piengine.visual.fog.Fog;
import piengine.visual.framebuffer.domain.Framebuffer;
import piengine.visual.framebuffer.domain.FramebufferKey;
import piengine.visual.framebuffer.manager.FramebufferManager;
import piengine.visual.render.domain.plan.RenderPlan;
import piengine.visual.render.domain.plan.RenderPlanBuilder;
import piengine.visual.render.manager.RenderManager;
import piengine.visual.skybox.domain.Skybox;
import piengine.visual.skybox.domain.SkyboxKey;
import piengine.visual.skybox.manager.SkyboxManager;
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
import static piengine.visual.camera.domain.ProjectionType.PERSPECTIVE;
import static piengine.visual.framebuffer.domain.FramebufferAttachment.COLOR_ATTACHMENT;
import static piengine.visual.framebuffer.domain.FramebufferAttachment.RENDER_BUFFER_ATTACHMENT;

public class InitScene extends Scene {

    private static final Vector2i VIEWPORT = new Vector2i(get(CAMERA_VIEWPORT_WIDTH), get(CAMERA_VIEWPORT_HEIGHT));

    private final InputManager inputManager;
    private final WindowManager windowManager;
    private final FramebufferManager framebufferManager;
    private final CubeMapManager cubeMapManager;
    private final SkyboxManager skyboxManager;
    private final CanvasManager canvasManager;

    private Framebuffer framebuffer;
    private Fog fog;
    private CubeMap cubeMap;
    private Skybox skybox;
    private Canvas mainCanvas;
    private FirstPersonCamera camera;

    private FirstPersonCameraAsset cameraAsset;
    private LampAsset lampAsset;
    private MapAsset mapAsset;
    private ButtonAsset buttonAsset;
    private FpsAsset fpsAsset;

    @Wire
    public InitScene(final RenderManager renderManager, final AssetManager assetManager,
                     final InputManager inputManager, final WindowManager windowManager,
                     final FramebufferManager framebufferManager, final CubeMapManager cubeMapManager,
                     final SkyboxManager skyboxManager, final CanvasManager canvasManager) {
        super(renderManager, assetManager);

        this.inputManager = inputManager;
        this.windowManager = windowManager;
        this.framebufferManager = framebufferManager;
        this.cubeMapManager = cubeMapManager;
        this.skyboxManager = skyboxManager;
        this.canvasManager = canvasManager;
    }

    @Override
    public void initialize() {
        super.initialize();
        inputManager.addEvent(GLFW_KEY_ESCAPE, PRESS, windowManager::closeWindow);
        inputManager.addEvent(GLFW_KEY_RIGHT_CONTROL, PRESS, () -> cameraAsset.lookingEnabled = !cameraAsset.lookingEnabled);
    }

    @Override
    protected void createAssets() {
        camera = new FirstPersonCamera(null, VIEWPORT, new CameraAttribute(get(CAMERA_FOV), get(CAMERA_NEAR_PLANE), get(CAMERA_FAR_PLANE)), PERSPECTIVE);

        mapAsset = createAsset(MapAsset.class, new MapAssetArgument(VIEWPORT, camera));

        cameraAsset = createAsset(FirstPersonCameraAsset.class, new CameraAssetArgument(
                mapAsset.getTerrains()[0],
                get(CAMERA_LOOK_UP_LIMIT),
                get(CAMERA_LOOK_DOWN_LIMIT),
                get(CAMERA_LOOK_SPEED),
                get(CAMERA_MOVE_SPEED)));

        //todo: ugly
        cameraAsset.addChild(camera);

        lampAsset = createAsset(LampAsset.class, new LampAssetArgument());

        framebuffer = framebufferManager.supply(new FramebufferKey(VIEWPORT, COLOR_ATTACHMENT, RENDER_BUFFER_ATTACHMENT));
        mainCanvas = canvasManager.supply(new CanvasKey(this, framebuffer));

        buttonAsset = createAsset(ButtonAsset.class, new ButtonAssetArgument(
                "buttonDefault", "buttonHover", "buttonPress",
                VIEWPORT, "Please press me!", () -> System.out.println("Button clicked!")));
        fpsAsset = createAsset(FpsAsset.class, new FpsAssetArgument());

        cubeMap = cubeMapManager.supply(new CubeMapKey(new String[]{
                "skybox/nightRight", "skybox/nightLeft",
                "skybox/nightTop", "skybox/nightBottom",
                "skybox/nightBack", "skybox/nightFront"
        }));
        skybox = skyboxManager.supply(new SkyboxKey(150f, cubeMap));

        fog = new Fog(ColorUtils.BLACK, 0.015f, 1.5f);
    }

    @Override
    protected void initializeAssets() {
        buttonAsset.setPosition(-0.75f, 0.875f, 0);

        cameraAsset.setPosition(-2, 0, 0);

        float lampX = 0;
        float lampZ = 0;
        float lampY = mapAsset.getTerrains()[0].getHeight(lampX, lampZ);
        lampAsset.setPosition(lampX, lampY, lampZ);
    }

    @Override
    public void update(double delta) {
        skybox.addRotation((float) (0.5f * delta));

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
                .loadCanvases(mainCanvas)
                .loadAssets(fpsAsset)
                .clearScreen(ColorUtils.BLACK)
                .render();
    }
}
