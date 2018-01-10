package piengine.visual.shadow.domain;

import org.joml.Vector2i;
import piengine.core.base.domain.ResourceData;
import piengine.visual.camera.domain.Camera;
import piengine.visual.light.Light;

public class ShadowData implements ResourceData {

    public final Camera playerCamera;
    public final Light light;
    public final Vector2i resolution;

    public ShadowData(final Camera playerCamera, final Light light, final Vector2i resolution) {
        this.playerCamera = playerCamera;
        this.light = light;
        this.resolution = resolution;
    }
}
