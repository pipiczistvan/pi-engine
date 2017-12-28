package piengine.visual.render.domain;

import org.joml.Vector2i;
import org.joml.Vector4f;
import piengine.object.asset.domain.Asset;
import piengine.visual.render.domain.fragment.RenderFragment;

import static piengine.visual.render.domain.fragment.domain.RenderFragmentType.DO_CLEAR_SCREEN;
import static piengine.visual.render.domain.fragment.domain.RenderFragmentType.DO_RENDER;
import static piengine.visual.render.domain.fragment.domain.RenderFragmentType.SET_ASSET;
import static piengine.visual.render.domain.fragment.domain.RenderFragmentType.SET_CLEAR_COLOR;
import static piengine.visual.render.domain.fragment.domain.RenderFragmentType.SET_VIEWPORT;

public class ScenePlan extends RenderPlan {

    private ScenePlan() {
    }

    public static ScenePlan createPlan() {
        return new ScenePlan();
    }

    public ScenePlan withViewport(final Vector2i viewPort) {
        fragments.add(new RenderFragment<>(SET_VIEWPORT, viewPort));
        return this;
    }

    public ScenePlan withClearColor(final Vector4f clearColor) {
        fragments.add(new RenderFragment<>(SET_CLEAR_COLOR, clearColor));
        return this;
    }

    public ScenePlan withAsset(final Asset asset) {
        fragments.add(new RenderFragment<>(SET_ASSET, asset));
        fragments.addAll(asset.renderPlan.fragments);
        return this;
    }

    public ScenePlan doClearScreen() {
        fragments.add(new RenderFragment(DO_CLEAR_SCREEN));
        return this;
    }

    public ScenePlan doRender(RenderType renderType) {
        fragments.add(new RenderFragment<>(DO_RENDER, renderType));
        return this;
    }

}
