package piengine.visual.camera.asset;

import piengine.object.asset.domain.AssetArgument;
import piengine.object.terrain.domain.Terrain;

public class CameraAssetArgument implements AssetArgument {

    public final Terrain terrain;
    public final float lookUpLimit;
    public final float lookDownLimit;
    public final float lookSpeed;
    public final float moveSpeed;
    public final float strafeSpeed;

    public CameraAssetArgument(final Terrain terrain, final float lookUpLimit, final float lookDownLimit, final float lookSpeed, final float moveSpeed) {
        this.terrain = terrain;
        this.lookUpLimit = lookUpLimit;
        this.lookDownLimit = lookDownLimit;
        this.lookSpeed = lookSpeed;
        this.moveSpeed = moveSpeed;
        this.strafeSpeed = moveSpeed / (float) Math.sqrt(2);
    }

    public CameraAssetArgument(final float lookUpLimit, final float lookDownLimit, final float lookSpeed, final float moveSpeed) {
        this(null, lookUpLimit, lookDownLimit, lookSpeed, moveSpeed);
    }
}
