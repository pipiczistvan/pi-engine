package piengine.visual.render.domain.fragment.handler;

import piengine.core.utils.ColorUtils;
import piengine.object.water.domain.Water;
import piengine.visual.camera.domain.Camera;
import piengine.visual.framebuffer.service.FramebufferService;
import piengine.visual.render.domain.fragment.domain.RenderFragmentType;
import piengine.visual.render.domain.fragment.domain.RenderWorldPlanContext;
import piengine.visual.render.service.ClearScreenRenderService;
import piengine.visual.render.service.ModelRenderService;
import piengine.visual.render.service.ShadowRenderService;
import piengine.visual.render.service.SkyboxRenderService;
import piengine.visual.render.service.TerrainRenderService;
import piengine.visual.render.service.WaterRenderService;
import piengine.visual.shadow.domain.Shadow;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import static piengine.visual.render.domain.fragment.domain.RenderFragmentType.RENDER_WORLD;

@Component
public class RenderWorldFragmentHandler implements FragmentHandler<RenderWorldPlanContext> {

    private final ModelRenderService modelRenderService;
    private final TerrainRenderService terrainRenderService;
    private final WaterRenderService waterRenderService;
    private final ShadowRenderService shadowRenderService;
    private final SkyboxRenderService skyboxRenderService;
    private final FramebufferService framebufferService;
    private final ClearScreenRenderService clearScreenRenderService;

    @Wire
    public RenderWorldFragmentHandler(final ModelRenderService modelRenderService, final TerrainRenderService terrainRenderService,
                                      final WaterRenderService waterRenderService, final ShadowRenderService shadowRenderService,
                                      final SkyboxRenderService skyboxRenderService, final FramebufferService framebufferService,
                                      final ClearScreenRenderService clearScreenRenderService) {
        this.modelRenderService = modelRenderService;
        this.terrainRenderService = terrainRenderService;
        this.waterRenderService = waterRenderService;
        this.shadowRenderService = shadowRenderService;
        this.skyboxRenderService = skyboxRenderService;
        this.framebufferService = framebufferService;
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
            framebufferService.bind(shadow.shadowMap);
            {
                context.camera = shadow.lightCamera;
                context.clippingPlane.set(0, 0, 0, 0);
                context.viewport.set(shadow.shadowMap.resolution);
                clearScreenRenderService.clearScreen(ColorUtils.BLACK);
                shadowRenderService.process(context);
            }
            framebufferService.unbind();
        }

        context.camera = camera;
    }

    private void renderToWater(final RenderWorldPlanContext context) {
        float cameraHeight = context.camera.getPosition().y;
        float cameraPitch = context.camera.getRotation().y;
        for (Water water : context.waters) {
            float waterHeight = water.position.y;
            float distance = 2 * (cameraHeight - waterHeight);

            framebufferService.bind(water.reflectionBuffer);
            {
                context.camera.translateRotate(0, -distance, 0, 0, -cameraPitch * 2, 0);

                context.clippingPlane.set(0, 1, 0, -waterHeight + 0.1f);
                context.viewport.set(water.reflectionBuffer.resolution);
                clearScreenRenderService.clearScreen(ColorUtils.BLACK);
                terrainRenderService.process(context);
                modelRenderService.process(context);
                skyboxRenderService.process(context);

                context.camera.translateRotate(0, distance, 0, 0, cameraPitch * 2, 0);
            }
            framebufferService.unbind();

            framebufferService.bind(water.refractionBuffer);
            {
                context.clippingPlane.set(0, -1, 0, waterHeight + 1);
                context.viewport.set(water.refractionBuffer.resolution);
                clearScreenRenderService.clearScreen(ColorUtils.BLACK);
                terrainRenderService.process(context);
                modelRenderService.process(context);
                skyboxRenderService.process(context);
            }
            framebufferService.unbind();
        }
    }

    private void renderToWorld(final RenderWorldPlanContext context) {
        context.clippingPlane.set(0, 0, 0, 0);
        context.viewport.set(context.camera.viewport);
        terrainRenderService.process(context);
        modelRenderService.process(context);
        skyboxRenderService.process(context);
        waterRenderService.process(context);
    }
}
