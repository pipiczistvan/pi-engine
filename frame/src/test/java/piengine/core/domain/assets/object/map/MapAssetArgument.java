package piengine.core.domain.assets.object.map;

import org.joml.Vector2i;
import piengine.object.asset.domain.AssetArgument;
import piengine.object.camera.domain.Camera;
import piengine.object.terrain.domain.Terrain;

public class MapAssetArgument implements AssetArgument {

    public final Vector2i viewport;
    public final Camera camera;
    public final Terrain terrain;

    public MapAssetArgument(final Vector2i viewport, final Camera camera, final Terrain terrain) {
        this.viewport = viewport;
        this.camera = camera;
        this.terrain = terrain;
    }
}
