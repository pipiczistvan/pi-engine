package piengine.core.domain.assets.object.cube;

import piengine.object.asset.domain.AssetArgument;
import piengine.visual.camera.asset.CameraAsset;
import piengine.visual.light.Light;

public class CubeAssetArgument implements AssetArgument {

    public final CameraAsset cameraAsset;
    public final Light light;

    public CubeAssetArgument(final CameraAsset cameraAsset, final Light light) {
        this.cameraAsset = cameraAsset;
        this.light = light;
    }
}
