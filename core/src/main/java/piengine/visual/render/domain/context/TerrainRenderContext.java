package piengine.visual.render.domain.context;

import piengine.object.terrain.domain.Terrain;
import piengine.visual.camera.domain.Camera;
import piengine.visual.light.Light;

public class TerrainRenderContext implements RenderContext {

    public final Camera camera;
    public final Light light;
    public final Terrain[] terrains;

    public TerrainRenderContext(final Camera camera, final Light light, final Terrain[] terrains) {
        this.camera = camera;
        this.light = light;
        this.terrains = terrains;
    }
}
