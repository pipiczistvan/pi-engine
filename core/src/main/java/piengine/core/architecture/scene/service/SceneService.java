package piengine.core.architecture.scene.service;

import piengine.core.architecture.scene.domain.Scene;
import piengine.core.base.api.Initializable;
import piengine.core.base.api.Renderable;
import piengine.core.base.api.Service;
import piengine.core.base.api.Updatable;
import piengine.visual.render.service.RenderService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import java.util.List;

@Component
public class SceneService implements Service, Initializable, Updatable, Renderable {

    private final RenderService renderService;
    private final List<Scene> scenes;

    @Wire
    public SceneService(final RenderService renderService, final List<Scene> scenes) {
        this.renderService = renderService;
        this.scenes = scenes;
    }

    @Override
    public void initialize() {
        scenes.forEach(scene -> {
            scene.initialize();
            scene.setupRenderPlan();
            renderService.validate(scene.renderPlan);
        });
    }

    @Override
    public void update(double delta) {
        scenes.forEach(scene -> scene.update(delta));
    }

    @Override
    public void render() {
        scenes.forEach(Scene::render);
    }

}
