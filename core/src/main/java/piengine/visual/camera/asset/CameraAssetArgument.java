package piengine.visual.camera.asset;

import piengine.object.asset.domain.AssetArgument;
import piengine.object.terrain.domain.Terrain;
import piengine.visual.camera.domain.Camera;

public class CameraAssetArgument<C extends Camera> implements AssetArgument {

    public final C camera;
    public final Terrain terrain;
    public final float lookUpLimit;
    public final float lookDownLimit;
    public final float lookSpeed;
    public final float moveSpeed;
    public final float strafeSpeed;

    public CameraAssetArgument(final C camera, final Terrain terrain, final float lookUpLimit, final float lookDownLimit, final float lookSpeed, final float moveSpeed) {
        this.camera = camera;
        this.terrain = terrain;
        this.lookUpLimit = lookUpLimit;
        this.lookDownLimit = lookDownLimit;
        this.lookSpeed = lookSpeed;
        this.moveSpeed = moveSpeed;
        this.strafeSpeed = moveSpeed / (float) Math.sqrt(2);
    }

    public CameraAssetArgument(final C camera, final float lookUpLimit, final float lookDownLimit, final float lookSpeed, final float moveSpeed) {
        this(camera, null, lookUpLimit, lookDownLimit, lookSpeed, moveSpeed);
    }
}
