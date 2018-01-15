package piengine.visual.shadow.domain;

import org.joml.Vector2i;
import piengine.core.base.domain.Key;
import piengine.visual.camera.domain.Camera;
import piengine.visual.light.domain.Light;

public class ShadowKey implements Key {

    public final Light light;
    public final Camera playerCamera;
    public final Vector2i resolution;

    public ShadowKey(final Light light, final Camera playerCamera, final Vector2i resolution) {
        this.light = light;
        this.playerCamera = playerCamera;
        this.resolution = resolution;
    }
}
