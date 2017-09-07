package piengine.visual.render.domain;

import piengine.object.asset.domain.Asset;
import piengine.object.model.domain.Model;
import piengine.visual.camera.Camera;
import piengine.visual.light.Light;
import piengine.visual.render.domain.fragment.RenderFragment;
import piengine.visual.texture.domain.Texture;
import piengine.visual.writing.text.domain.Text;

import java.util.Collections;
import java.util.List;

import static piengine.visual.render.domain.fragment.domain.RenderFragmentType.SET_ASSET;
import static piengine.visual.render.domain.fragment.domain.RenderFragmentType.SET_CAMERA;
import static piengine.visual.render.domain.fragment.domain.RenderFragmentType.SET_LIGHT;
import static piengine.visual.render.domain.fragment.domain.RenderFragmentType.SET_MODELS;
import static piengine.visual.render.domain.fragment.domain.RenderFragmentType.SET_PLANET;
import static piengine.visual.render.domain.fragment.domain.RenderFragmentType.SET_TEXT;
import static piengine.visual.render.domain.fragment.domain.RenderFragmentType.SET_TEXTURE;

public class AssetPlan extends RenderPlan {

    private AssetPlan() {
    }

    public static AssetPlan createPlan() {
        return new AssetPlan();
    }

    public AssetPlan withModel(final Model model) {
        return withModels(Collections.singletonList(model));
    }

    public AssetPlan withModels(final List<Model> models) {
        fragments.add(new RenderFragment<>(SET_MODELS, models));
        return this;
    }

    public AssetPlan withTexture(final Texture texture) {
        fragments.add(new RenderFragment<>(SET_TEXTURE, texture));
        return this;
    }

    public AssetPlan withCamera(final Camera camera) {
        fragments.add(new RenderFragment<>(SET_CAMERA, camera));
        return this;
    }

    public AssetPlan withLight(final Light light) {
        fragments.add(new RenderFragment<>(SET_LIGHT, light));
        return this;
    }

    public AssetPlan withText(final Text text) {
        return withTexts(Collections.singletonList(text));
    }

    public AssetPlan withTexts(final List<Text> texts) {
        fragments.add(new RenderFragment<>(SET_TEXT, texts));
        return this;
    }

    public AssetPlan withPlanet(final Model planet) {
        fragments.add(new RenderFragment<>(SET_PLANET, planet));
        return this;
    }

    public AssetPlan withAsset(final Asset asset) {
        fragments.add(new RenderFragment<>(SET_ASSET, asset));
        fragments.addAll(asset.renderPlan.fragments);
        return this;
    }

}
