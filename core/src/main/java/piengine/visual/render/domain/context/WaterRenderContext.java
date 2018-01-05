package piengine.visual.render.domain.context;

import piengine.object.water.domain.Water;
import piengine.visual.camera.domain.Camera;

public class WaterRenderContext implements RenderContext {

    public final Camera camera;
    public final Water water;

    public WaterRenderContext(final Camera camera, final Water water) {
        this.camera = camera;
        this.water = water;
    }
}
