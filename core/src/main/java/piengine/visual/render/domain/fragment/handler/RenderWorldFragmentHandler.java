package piengine.visual.render.domain.fragment.handler;

import piengine.core.utils.ColorUtils;
import piengine.object.water.domain.Water;
import piengine.visual.framebuffer.service.FrameBufferService;
import piengine.visual.render.domain.fragment.domain.RenderFragmentType;
import piengine.visual.render.domain.fragment.domain.RenderWorldPlanContext;
import piengine.visual.render.service.ClearScreenRenderService;
import piengine.visual.render.service.TerrainRenderService;
import piengine.visual.render.service.WaterRenderService;
import piengine.visual.render.service.WorldRenderService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import static piengine.visual.render.domain.fragment.domain.RenderFragmentType.RENDER_WORLD;

@Component
public class RenderWorldFragmentHandler implements FragmentHandler<RenderWorldPlanContext> {

    private final WorldRenderService worldRenderService;
    private final TerrainRenderService terrainRenderService;
    private final WaterRenderService waterRenderService;
    private final FrameBufferService frameBufferService;
    private final ClearScreenRenderService clearScreenRenderService;

    @Wire
    public RenderWorldFragmentHandler(final WorldRenderService worldRenderService, final TerrainRenderService terrainRenderService,
                                      final WaterRenderService waterRenderService, final FrameBufferService frameBufferService,
                                      final ClearScreenRenderService clearScreenRenderService) {
        this.worldRenderService = worldRenderService;
        this.terrainRenderService = terrainRenderService;
        this.waterRenderService = waterRenderService;
        this.frameBufferService = frameBufferService;
        this.clearScreenRenderService = clearScreenRenderService;
    }

    @Override
    public void handle(final RenderWorldPlanContext context) {
        float cameraHeight = context.camera.getPosition().y;
        float cameraPitch = context.camera.getRotation().y;

        for (Water water : context.waters) {
            float waterHeight = water.getPosition().y;
            float distance = 2 * (cameraHeight - waterHeight);

            frameBufferService.bind(water.reflectionBuffer);
            {
                context.camera.addPosition(0, -distance, 0);
                context.camera.addRotation(0, -cameraPitch * 2, 0);

                context.clippingPlane.set(0, 1, 0, -waterHeight + 0.1f);
                clearScreenRenderService.clearScreen(ColorUtils.WHITE);
                terrainRenderService.process(context);
                worldRenderService.process(context);

                context.camera.addPosition(0, distance, 0);
                context.camera.addRotation(0, cameraPitch * 2, 0);
            }
            frameBufferService.unbind();

            frameBufferService.bind(water.refractionBuffer);
            {
                context.clippingPlane.set(0, -1, 0, waterHeight + 1);
                clearScreenRenderService.clearScreen(ColorUtils.WHITE);
                terrainRenderService.process(context);
                worldRenderService.process(context);
            }
            frameBufferService.unbind();
        }

        context.clippingPlane.set(0, 0, 0, 0);
        terrainRenderService.process(context);
        worldRenderService.process(context);

        waterRenderService.process(context);
    }

    @Override
    public RenderFragmentType getType() {
        return RENDER_WORLD;
    }
}
