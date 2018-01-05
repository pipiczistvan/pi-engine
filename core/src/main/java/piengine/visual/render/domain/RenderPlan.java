package piengine.visual.render.domain;

import org.joml.Vector2i;
import piengine.core.base.type.color.Color;
import piengine.object.asset.domain.Asset;
import piengine.object.model.domain.Model;
import piengine.object.terrain.domain.Terrain;
import piengine.object.water.domain.Water;
import piengine.visual.camera.asset.CameraAsset;
import piengine.visual.framebuffer.domain.FrameBuffer;
import piengine.visual.light.Light;
import piengine.visual.render.domain.context.GuiRenderContext;
import piengine.visual.render.domain.context.TerrainRenderContext;
import piengine.visual.render.domain.context.WaterRenderContext;
import piengine.visual.render.domain.context.WorldRenderContext;
import piengine.visual.render.domain.fragment.RenderFragment;
import piengine.visual.writing.text.domain.Text;
import piengine.visual.writing.text.domain.TextRenderContext;

import java.util.ArrayList;
import java.util.List;

import static piengine.visual.render.domain.fragment.domain.RenderFragmentType.BIND_FRAME_BUFFER;
import static piengine.visual.render.domain.fragment.domain.RenderFragmentType.CLEAR_SCREEN;
import static piengine.visual.render.domain.fragment.domain.RenderFragmentType.RENDER_TERRAIN;
import static piengine.visual.render.domain.fragment.domain.RenderFragmentType.RENDER_TEXT;
import static piengine.visual.render.domain.fragment.domain.RenderFragmentType.RENDER_TO_GUI;
import static piengine.visual.render.domain.fragment.domain.RenderFragmentType.RENDER_TO_WORLD;
import static piengine.visual.render.domain.fragment.domain.RenderFragmentType.RENDER_WATER;

public class RenderPlan {

    public final List<RenderFragment> fragments = new ArrayList<>();

    private RenderPlan() {
    }

    public static RenderPlan createPlan() {
        return new RenderPlan();
    }

    public RenderPlan clearScreen(final Color color) {
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

    public RenderPlan renderText(final Vector2i viewport, final Text... texts) {
        fragments.add(new RenderFragment<>(RENDER_TEXT, new TextRenderContext(viewport, texts)));
        return this;
    }

    public RenderPlan renderTerrain(final CameraAsset cameraAsset, final Light light, final Terrain... terrains) {
        fragments.add(new RenderFragment<>(RENDER_TERRAIN, new TerrainRenderContext(cameraAsset.camera, light, terrains)));
        return this;
    }

    public RenderPlan renderWater(final CameraAsset cameraAsset, final Water water) {
        fragments.add(new RenderFragment<>(RENDER_WATER, new WaterRenderContext(cameraAsset.camera, water)));
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
