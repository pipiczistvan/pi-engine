package piengine.core.architecture.scene.service;

import piengine.core.architecture.scene.domain.Scene;
import piengine.core.base.api.Initializable;
import piengine.core.base.api.Renderable;
import piengine.core.base.api.Resizable;
import piengine.core.base.api.Service;
import piengine.core.base.api.Updatable;
import puppeteer.Puppeteer;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

@Component
public class SceneService implements Service, Initializable, Updatable, Renderable, Resizable {

    private final Puppeteer puppeteer;

    private Class<? extends Scene> defaultSceneClass;
    private Scene currentScene;

    @Wire
    public SceneService(final Puppeteer puppeteer) {
        this.puppeteer = puppeteer;
    }

    @Override
    public void initialize() {
        setCurrentScene(defaultSceneClass);
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

    @Override
    public void resize(final int oldWidth, final int oldHeight, final int width, final int height) {
        currentScene.resize(oldWidth, oldHeight, width, height);
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
