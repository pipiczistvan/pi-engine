package piengine.visual.render.domain.context;

import piengine.object.model.domain.Model;
import piengine.visual.camera.domain.Camera;
import piengine.visual.light.Light;

public class WorldRenderContext implements RenderContext {

    public final Camera camera;
    public final Light light;
    public final Model[] models;

    public WorldRenderContext(final Camera camera, final Light light, final Model[] models) {
        this.camera = camera;
        this.light = light;
        this.models = models;
    }
}
