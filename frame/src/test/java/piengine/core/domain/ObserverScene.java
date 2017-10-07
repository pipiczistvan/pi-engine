package piengine.core.domain;

import org.joml.Vector2i;
import org.joml.Vector4f;
import piengine.core.architecture.scene.domain.Scene;
import piengine.core.domain.assets.StickAsset;
import piengine.core.domain.assets.TextAsset;
import piengine.core.input.manager.InputManager;
import piengine.object.asset.domain.ObserverCamera;
import piengine.object.asset.manager.AssetManager;
import piengine.visual.render.domain.ScenePlan;
import piengine.visual.render.manager.RenderManager;
import piengine.visual.window.manager.WindowManager;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static piengine.core.input.domain.KeyEventType.PRESS;
import static piengine.visual.render.domain.RenderType.RENDER_SOLID_MODEL;
import static piengine.visual.render.domain.RenderType.RENDER_TEXT;
import static piengine.visual.render.domain.ScenePlan.createPlan;

@Component
public class ObserverScene extends Scene {

    private final InputManager inputManager;
    private final WindowManager windowManager;

    private ObserverCamera observerCamera;
    private StickAsset stickAsset;
    private TextAsset textAsset;

    @Wire
    public ObserverScene(final RenderManager renderManager, final AssetManager assetManager,
                         final InputManager inputManager, final WindowManager windowManager) {
        super(renderManager, assetManager);
        this.inputManager = inputManager;
        this.windowManager = windowManager;
    }

    @Override
    public void initialize() {
        observerCamera = createAsset(ObserverCamera.class);
        textAsset = createAsset(TextAsset.class);
        stickAsset = createAsset(StickAsset.class);

        inputManager.addEvent(GLFW_KEY_ESCAPE, PRESS, windowManager::closeWindow);
    }

    @Override
    protected ScenePlan createRenderPlan() {
        return createPlan()
                .withViewPort(new Vector2i(800, 600))
                .withClearColor(new Vector4f(1, 1, 1, 1))
                .doClearScreen()
                .withAsset(observerCamera)
                .withAsset(stickAsset)
                .doRender(RENDER_SOLID_MODEL)
                .withAsset(textAsset)
                .doRender(RENDER_TEXT);
    }

}
