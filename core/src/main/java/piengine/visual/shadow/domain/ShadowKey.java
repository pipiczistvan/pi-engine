package piengine.visual.shadow.domain;

import org.joml.Vector2i;
import piengine.visual.camera.domain.Camera;
import piengine.visual.light.Light;

import java.util.Objects;

public class ShadowKey {

    public final Light light;
    public final Camera playerCamera;
    public final Vector2i resolution;

    public ShadowKey(final Light light, final Camera playerCamera, final Vector2i resolution) {
        this.light = light;
        this.playerCamera = playerCamera;
        this.resolution = resolution;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShadowKey shadowKey = (ShadowKey) o;
        return Objects.equals(light, shadowKey.light) &&
                Objects.equals(playerCamera, shadowKey.playerCamera) &&
                Objects.equals(resolution, shadowKey.resolution);
    }

    @Override
    public int hashCode() {

        return Objects.hash(light, playerCamera, resolution);
    }
}
