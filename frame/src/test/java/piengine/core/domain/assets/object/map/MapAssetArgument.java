package piengine.core.domain.assets.object.map;

import org.joml.Vector2i;
import piengine.object.asset.domain.AssetArgument;
import piengine.visual.camera.domain.Camera;

public class MapAssetArgument implements AssetArgument {

    public final Vector2i viewport;
    public final Camera camera;

    public MapAssetArgument(final Vector2i viewport, final Camera camera) {
        this.viewport = viewport;
        this.camera = camera;
    }
}
