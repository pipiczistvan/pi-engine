package piengine.visual.lighting.directional.shadow.domain;

import org.joml.Vector2i;
import piengine.core.base.domain.ResourceData;
import piengine.object.camera.domain.Camera;

public class DirectionalShadowData implements ResourceData {

    public final Camera playerCamera;
    public final Vector2i resolution;

    public DirectionalShadowData(final Camera playerCamera, final Vector2i resolution) {
        this.playerCamera = playerCamera;
        this.resolution = resolution;
    }
}
