package piengine.core.architecture.scene.service;

import piengine.core.architecture.scene.domain.Scene;
import piengine.core.base.api.Initializable;
import piengine.core.base.api.Renderable;
import piengine.core.base.api.Service;
import piengine.core.base.api.Updatable;
import piengine.visual.window.service.WindowService;
import puppeteer.Puppeteer;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import static piengine.visual.window.domain.WindowEventType.RESIZE;

@Component
public class SceneService implements Service, Initializable, Updatable, Renderable {

    private final Puppeteer puppeteer;
    private final WindowService windowService;

    private Class<? extends Scene> defaultSceneClass;
    private Scene currentScene;

    @Wire
    public SceneService(final Puppeteer puppeteer,
                        final WindowService windowService) {
        this.puppeteer = puppeteer;
        this.windowService = windowService;
    }

    @Override
    public void initialize() {
        setCurrentScene(defaultSceneClass);
        windowService.addEvent(RESIZE, () -> {
            currentScene.initialize();
            currentScene.setupRenderPlan();
        });
    }

    @Override
    public void update(final float delta) {
        if (currentScene != null) {
            currentScene.update(delta);
        }
    }

    @Override
    public void render() {
        if (currentScene != null) {
            currentScene.render();
        }
    }

    public void setDefaultSceneClass(Class<? extends Scene> defaultSceneClass) {
        this.defaultSceneClass = defaultSceneClass;
    }

    public void setCurrentScene(Class<? extends Scene> sceneClass) {
        currentScene = puppeteer.getInstanceOf(sceneClass);

        currentScene.initialize();
        currentScene.setupRenderPlan();
    }
}
