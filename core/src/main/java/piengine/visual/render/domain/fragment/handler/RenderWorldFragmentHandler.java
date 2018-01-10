package piengine.visual.render.domain.fragment.handler;

import piengine.core.utils.ColorUtils;
import piengine.object.water.domain.Water;
import piengine.visual.camera.domain.Camera;
import piengine.visual.framebuffer.service.FrameBufferService;
import piengine.visual.render.domain.fragment.domain.RenderFragmentType;
import piengine.visual.render.domain.fragment.domain.RenderWorldPlanContext;
import piengine.visual.render.service.ClearScreenRenderService;
import piengine.visual.render.service.ShadowRenderService;
import piengine.visual.render.service.SkyboxRenderService;
import piengine.visual.render.service.TerrainRenderService;
import piengine.visual.render.service.WaterRenderService;
import piengine.visual.render.service.WorldRenderService;
import piengine.visual.shadow.domain.Shadow;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import static piengine.visual.render.domain.fragment.domain.RenderFragmentType.RENDER_WORLD;

@Component
public class RenderWorldFragmentHandler implements FragmentHandler<RenderWorldPlanContext> {

    private final WorldRenderService worldRenderService;
    private final TerrainRenderService terrainRenderService;
    private final WaterRenderService waterRenderService;
    private final ShadowRenderService shadowRenderService;
    private final SkyboxRenderService skyboxRenderService;
    private final FrameBufferService frameBufferService;
    private final ClearScreenRenderService clearScreenRenderService;

    @Wire
    public RenderWorldFragmentHandler(final WorldRenderService worldRenderService, final TerrainRenderService terrainRenderService,
                                      final WaterRenderService waterRenderService, final ShadowRenderService shadowRenderService,
                                      final SkyboxRenderService skyboxRenderService, final FrameBufferService frameBufferService,
                                      final ClearScreenRenderService clearScreenRenderService) {
        this.worldRenderService = worldRenderService;
        this.terrainRenderService = terrainRenderService;
        this.waterRenderService = waterRenderService;
        this.shadowRenderService = shadowRenderService;
        this.skyboxRenderService = skyboxRenderService;
        this.frameBufferService = frameBufferService;
        this.clearScreenRenderService = clearScreenRenderService;
    }

    @Override
    public void handle(final RenderWorldPlanContext context) {
        renderToShadow(context);
        renderToWater(context);
        renderToWorld(context);
    }

    @Override
    public RenderFragmentType getType() {
        return RENDER_WORLD;
    }

    private void renderToShadow(final RenderWorldPlanContext context) {
        Camera camera = context.camera;

        for (Shadow shadow : context.shadows) {
            frameBufferService.bind(shadow.shadowMap);
            {
                context.camera = shadow.lightCamera;
                context.clippingPlane.set(0, 0, 0, 0);
                context.viewport.set(shadow.shadowMap.resolution);
                clearScreenRenderService.clearScreen(ColorUtils.BLACK);
                shadowRenderService.process(context);
            }
            frameBufferService.unbind();
        }

        context.camera = camera;
    }

    private void renderToWater(final RenderWorldPlanContext context) {
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
                context.viewport.set(water.reflectionBuffer.resolution);
                clearScreenRenderService.clearScreen(ColorUtils.BLACK);
                terrainRenderService.process(context);
                worldRenderService.process(context);
                skyboxRenderService.process(context);

                context.camera.addPosition(0, distance, 0);
                context.camera.addRotation(0, cameraPitch * 2, 0);
            }
            frameBufferService.unbind();

            frameBufferService.bind(water.refractionBuffer);
            {
                context.clippingPlane.set(0, -1, 0, waterHeight + 1);
                context.viewport.set(water.refractionBuffer.resolution);
                clearScreenRenderService.clearScreen(ColorUtils.BLACK);
                terrainRenderService.process(context);
                worldRenderService.process(context);
                skyboxRenderService.process(context);
            }
            frameBufferService.unbind();
        }
    }

    private void renderToWorld(final RenderWorldPlanContext context) {
        context.clippingPlane.set(0, 0, 0, 0);
        context.viewport.set(context.camera.viewport);
        terrainRenderService.process(context);
        worldRenderService.process(context);
        skyboxRenderService.process(context);
        waterRenderService.process(context);
    }
}
