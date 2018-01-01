package piengine.visual.render.domain;

import org.joml.Vector2i;
import org.joml.Vector4f;
import piengine.object.asset.domain.Asset;
import piengine.object.model.domain.Model;
import piengine.visual.camera.asset.CameraAsset;
import piengine.visual.framebuffer.domain.FrameBuffer;
import piengine.visual.light.Light;
import piengine.visual.render.domain.context.GuiRenderContext;
import piengine.visual.render.domain.context.WorldRenderContext;
import piengine.visual.render.domain.fragment.RenderFragment;

import java.util.ArrayList;
import java.util.List;

import static piengine.visual.render.domain.fragment.domain.RenderFragmentType.*;

public class RenderPlan {

    public final List<RenderFragment> fragments = new ArrayList<>();

    private RenderPlan() {
    }

    public static RenderPlan createPlan() {
        return new RenderPlan();
    }

    public RenderPlan clearScreen(final Vector4f color) {
        fragments.add(new RenderFragment<>(CLEAR_SCREEN, color));
        return this;
    }

    public RenderPlan renderToFrameBuffer(final FrameBuffer frameBuffer, final RenderPlan plan) {
        fragments.add(new RenderFragment<>(BIND_FRAME_BUFFER, frameBuffer));
        fragments.addAll(plan.fragments);
        fragments.add(new RenderFragment<>(BIND_FRAME_BUFFER, null));
        return this;
    }

    public RenderPlan renderToGui(final Vector2i viewport, final Model... models) {
        fragments.add(new RenderFragment<>(RENDER_TO_GUI, new GuiRenderContext(viewport, models)));
        return this;
    }

    public RenderPlan renderToWorld(final CameraAsset cameraAsset, final Light light, final Model... models) {
        fragments.add(new RenderFragment<>(RENDER_TO_WORLD, new WorldRenderContext(cameraAsset.camera, light, models)));
        return this;
    }

    public RenderPlan loadAsset(final Asset asset) {
        fragments.addAll(asset.renderPlan.fragments);
        return this;
    }
}
