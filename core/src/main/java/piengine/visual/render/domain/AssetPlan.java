package piengine.visual.render.domain;

import org.joml.Vector2i;
import org.joml.Vector4f;
import piengine.common.gui.writing.text.domain.Text;
import piengine.object.asset.domain.Asset;
import piengine.object.model.domain.Model;
import piengine.visual.camera.Camera;
import piengine.visual.light.Light;
import piengine.visual.render.domain.fragment.RenderFragment;
import piengine.visual.texture.domain.Texture;

import java.util.Collections;
import java.util.List;

import static piengine.visual.render.domain.fragment.domain.RenderFragmentType.DO_CLEAR_SCREEN;
import static piengine.visual.render.domain.fragment.domain.RenderFragmentType.DO_RENDER;
import static piengine.visual.render.domain.fragment.domain.RenderFragmentType.SET_CAMERA;
import static piengine.visual.render.domain.fragment.domain.RenderFragmentType.SET_CLEAR_COLOR;
import static piengine.visual.render.domain.fragment.domain.RenderFragmentType.SET_COLOR;
import static piengine.visual.render.domain.fragment.domain.RenderFragmentType.SET_LIGHT;
import static piengine.visual.render.domain.fragment.domain.RenderFragmentType.SET_MODELS;
import static piengine.visual.render.domain.fragment.domain.RenderFragmentType.SET_PLANET;
import static piengine.visual.render.domain.fragment.domain.RenderFragmentType.SET_TEXT;
import static piengine.visual.render.domain.fragment.domain.RenderFragmentType.SET_TEXTURE;
import static piengine.visual.render.domain.fragment.domain.RenderFragmentType.SET_VIEWPORT;

public class AssetPlan extends RenderPlan {

    private AssetPlan() {
    }

    public static AssetPlan createPlan() {
        return new AssetPlan();
    }

    public AssetPlan withPlan(final AssetPlan assetPlan) {
        fragments.addAll(assetPlan.fragments);
        return this;
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

    public AssetPlan withColor(final Vector4f color) {
        fragments.add(new RenderFragment<>(SET_COLOR, color));
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

    public AssetPlan withViewport(final Vector2i viewport) {
        fragments.add(new RenderFragment<>(SET_VIEWPORT, viewport));
        return this;
    }

    public AssetPlan withClearColor(final Vector4f clearColor) {
        fragments.add(new RenderFragment<>(SET_CLEAR_COLOR, clearColor));
        return this;
    }

    public AssetPlan doClearScreen() {
        fragments.add(new RenderFragment<>(DO_CLEAR_SCREEN));
        return this;
    }

    public AssetPlan doRender(final RenderType renderType) {
        fragments.add(new RenderFragment<>(DO_RENDER, renderType));
        return this;
    }

    public AssetPlan withAsset(final Asset asset) {
        fragments.addAll(asset.renderPlan.fragments);
        return this;
    }

}
