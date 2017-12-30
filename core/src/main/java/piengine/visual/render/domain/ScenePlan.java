package piengine.visual.render.domain;

import org.joml.Vector2i;
import org.joml.Vector4f;
import piengine.object.asset.domain.Asset;
import piengine.visual.framebuffer.domain.FrameBuffer;
import piengine.visual.render.domain.fragment.RenderFragment;

import static piengine.visual.render.domain.fragment.domain.RenderFragmentType.*;

public class ScenePlan extends RenderPlan {

    private ScenePlan() {
    }

    public static ScenePlan createPlan() {
        return new ScenePlan();
    }

    public ScenePlan withClearColor(final Vector4f clearColor) {
        fragments.add(new RenderFragment<>(SET_CLEAR_COLOR, clearColor));
        return this;
    }

    public ScenePlan withAsset(final Asset asset) {
        fragments.addAll(asset.renderPlan.fragments);
        return this;
    }

    public ScenePlan withScenePlan(final ScenePlan plan) {
        fragments.addAll(plan.fragments);
        return this;
    }

    public ScenePlan doClearScreen() {
        fragments.add(new RenderFragment(DO_CLEAR_SCREEN));
        return this;
    }

    public ScenePlan doRender(final RenderType renderType) {
        fragments.add(new RenderFragment<>(DO_RENDER, renderType));
        return this;
    }

    public ScenePlan doBindFrameBuffer(final FrameBuffer frameBuffer) {
        fragments.add(new RenderFragment<>(DO_BIND_FRAME_BUFFER, frameBuffer));
        return this;
    }

    public ScenePlan doUnbindFrameBuffer() {
        fragments.add(new RenderFragment<>(DO_UNBIND_FRAME_BUFFER));
        return this;
    }
}
