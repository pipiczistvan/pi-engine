package piengine.visual.render.domain.plan;

import org.joml.Vector2i;
import piengine.core.base.type.color.Color;
import piengine.visual.camera.domain.Camera;
import piengine.visual.fog.Fog;
import piengine.visual.framebuffer.domain.Framebuffer;
import piengine.visual.render.domain.fragment.RenderFragment;
import piengine.visual.render.domain.fragment.domain.BindFrameBufferPlanContext;
import piengine.visual.render.domain.fragment.domain.ClearScreenPlanContext;
import piengine.visual.render.domain.fragment.domain.RenderFragmentType;
import piengine.visual.skybox.domain.Skybox;

import java.util.ArrayList;
import java.util.List;

import static piengine.visual.render.domain.fragment.domain.RenderFragmentType.BIND_FRAME_BUFFER;
import static piengine.visual.render.domain.fragment.domain.RenderFragmentType.CLEAR_SCREEN;

public abstract class RenderPlanBuilder<B extends RenderPlanBuilder<B, C>, C extends PlanContext> {

    private final List<RenderFragment> fragments = new ArrayList<>();
    protected final C context;

    RenderPlanBuilder(final C context) {
        this.context = context;
    }

    public static WorldRenderPlanBuilder createPlan(final Camera camera, final Fog fog, final Skybox skybox) {
        return new WorldRenderPlanBuilder(camera, fog, skybox);
    }

    public static GuiRenderPlanBuilder createPlan(final Vector2i viewport) {
        return new GuiRenderPlanBuilder(viewport);
    }

    public B clearScreen(final Color color) {
        fragments.add(new RenderFragment<>(CLEAR_SCREEN, new ClearScreenPlanContext(color)));
        return thiz();
    }

    public B bindFrameBuffer(final Framebuffer framebuffer, final RenderPlan renderPlan) {
        fragments.add(new RenderFragment<>(BIND_FRAME_BUFFER, new BindFrameBufferPlanContext(framebuffer)));
        fragments.addAll(renderPlan.fragments);
        fragments.add(new RenderFragment<>(BIND_FRAME_BUFFER, new BindFrameBufferPlanContext()));
        return thiz();
    }

    public RenderPlan render() {
        fragments.add(new RenderFragment<>(getType(), context));
        return new RenderPlan(fragments);
    }

    protected abstract B thiz();

    protected abstract RenderFragmentType getType();
}
