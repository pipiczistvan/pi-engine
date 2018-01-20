package piengine.visual.lighting.directional.shadow.domain;

import org.joml.Vector2i;
import piengine.core.base.domain.Key;
import piengine.object.camera.domain.Camera;

public class DirectionalShadowKey implements Key {

    public final Camera playerCamera;
    public final Vector2i resolution;

    public DirectionalShadowKey(final Camera playerCamera, final Vector2i resolution) {
        this.playerCamera = playerCamera;
        this.resolution = resolution;
    }
}
