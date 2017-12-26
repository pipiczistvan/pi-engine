package piengine.core.domain;

import org.joml.Vector2i;
import org.joml.Vector4f;
import piengine.core.architecture.scene.domain.Scene;
import piengine.core.domain.assets.LightAsset;
import piengine.core.domain.assets.TextAsset;
import piengine.core.input.manager.InputManager;
import piengine.object.asset.domain.FirstPersonCamera;
import piengine.object.asset.manager.AssetManager;
import piengine.planet.asset.PlanetAsset;
import piengine.visual.render.domain.ScenePlan;
import piengine.visual.render.manager.RenderManager;
import piengine.visual.window.manager.WindowManager;
import puppeteer.annotation.premade.Wire;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static piengine.core.input.domain.KeyEventType.PRESS;
import static piengine.visual.render.domain.RenderType.RENDER_PLANET;
import static piengine.visual.render.domain.RenderType.RENDER_TEXT;
import static piengine.visual.render.domain.ScenePlan.createPlan;

public class InitScene extends Scene {

    private final InputManager inputManager;
    private final WindowManager windowManager;

    private FirstPersonCamera firstPersonCamera;
    private LightAsset lightAsset;
    private PlanetAsset planetAsset;
    private TextAsset textAsset;

    @Wire
    public InitScene(final RenderManager renderManager, final AssetManager assetManager,
                     final InputManager inputManager, final WindowManager windowManager) {
        super(renderManager, assetManager);
        this.inputManager = inputManager;
        this.windowManager = windowManager;
    }

    @Override
    public void initialize() {
        firstPersonCamera = createAsset(FirstPersonCamera.class);
        lightAsset = createAsset(LightAsset.class);
        planetAsset = createAsset(PlanetAsset.class);
        textAsset = createAsset(TextAsset.class);

        firstPersonCamera.setPosition(0, 0, 5);
        lightAsset.setPosition(5, 5, 5);

        inputManager.addEvent(GLFW_KEY_ESCAPE, PRESS, windowManager::closeWindow);
    }

    @Override
    protected ScenePlan createRenderPlan() {
        return createPlan()
                .withViewPort(new Vector2i(800, 600))
                .withClearColor(new Vector4f(0, 0, 0, 1))
                .doClearScreen()
                .withAsset(firstPersonCamera)
//                .withAsset(lightAsset)
                .withAsset(planetAsset)
                .doRender(RENDER_PLANET)
                .withAsset(textAsset)
                .doRender(RENDER_TEXT);
    }

}
