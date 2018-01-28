package piengine.visual.render.domain.fragment.handler;

import piengine.core.utils.ColorUtils;
import piengine.object.animatedmodel.service.AnimatedModelRenderService;
import piengine.object.model.service.ModelRenderService;
import piengine.object.particlesystem.service.ParticleSystemRenderService;
import piengine.object.skybox.service.SkyboxRenderService;
import piengine.object.terrain.service.TerrainRenderService;
import piengine.object.water.domain.Water;
import piengine.object.water.service.WaterRenderService;
import piengine.visual.framebuffer.service.FramebufferService;
import piengine.visual.lighting.directional.light.domain.DirectionalLight;
import piengine.visual.lighting.directional.shadow.domain.DirectionalShadow;
import piengine.visual.lighting.directional.shadow.service.DirectionalShadowRenderService;
import piengine.visual.lighting.point.light.domain.PointLight;
import piengine.visual.lighting.point.shadow.domain.PointShadow;
import piengine.visual.lighting.point.shadow.service.PointShadowRenderService;
import piengine.visual.render.domain.fragment.domain.RenderFragmentType;
import piengine.visual.render.domain.fragment.domain.RenderWorldPlanContext;
import piengine.visual.render.service.ClearScreenRenderService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import static piengine.visual.render.domain.fragment.domain.RenderFragmentType.RENDER_WORLD;

@Component
public class RenderWorldFragmentHandler implements FragmentHandler<RenderWorldPlanContext> {

    private final ModelRenderService modelRenderService;
    private final TerrainRenderService terrainRenderService;
    private final WaterRenderService waterRenderService;
    private final DirectionalShadowRenderService directionalShadowRenderService;
    private final SkyboxRenderService skyboxRenderService;
    private final FramebufferService framebufferService;
    private final ClearScreenRenderService clearScreenRenderService;
    private final PointShadowRenderService pointShadowRenderService;
    private final AnimatedModelRenderService animatedModelRenderService;
    private final ParticleSystemRenderService particleSystemRenderService;

    @Wire
    public RenderWorldFragmentHandler(final ModelRenderService modelRenderService, final TerrainRenderService terrainRenderService,
                                      final WaterRenderService waterRenderService, final DirectionalShadowRenderService directionalShadowRenderService,
                                      final SkyboxRenderService skyboxRenderService, final FramebufferService framebufferService,
                                      final ClearScreenRenderService clearScreenRenderService, final PointShadowRenderService pointShadowRenderService,
                                      final AnimatedModelRenderService animatedModelRenderService, final ParticleSystemRenderService particleSystemRenderService) {
        this.modelRenderService = modelRenderService;
        this.terrainRenderService = terrainRenderService;
        this.waterRenderService = waterRenderService;
        this.directionalShadowRenderService = directionalShadowRenderService;
        this.skyboxRenderService = skyboxRenderService;
        this.framebufferService = framebufferService;
        this.clearScreenRenderService = clearScreenRenderService;
        this.pointShadowRenderService = pointShadowRenderService;
        this.animatedModelRenderService = animatedModelRenderService;
        this.particleSystemRenderService = particleSystemRenderService;
    }

    @Override
    public void handle(final RenderWorldPlanContext context) {
        renderToDirectionalShadow(context);
        renderToPointShadow(context);
        renderToWater(context);
        renderToWorld(context);
    }

    @Override
    public RenderFragmentType getType() {
        return RENDER_WORLD;
    }

    private void renderToDirectionalShadow(final RenderWorldPlanContext context) {
        for (DirectionalLight light : context.directionalLights) {
            DirectionalShadow shadow = light.getShadow();
            if (shadow != null) {
                framebufferService.bind(shadow.getShadowMap());
                {
                    context.currentCamera = shadow.getLightCamera();
                    context.clippingPlane.set(0, 0, 0, 0);
                    context.viewport.set(shadow.getShadowMap().getSize());
                    clearScreenRenderService.clearScreen(ColorUtils.BLACK);
                    directionalShadowRenderService.process(context);
                }
                framebufferService.unbind();
            }
        }
    }

    private void renderToPointShadow(final RenderWorldPlanContext context) {
        for (PointLight light : context.pointLights) {
            PointShadow shadow = light.getShadow();
            if (shadow != null) {
                context.currentPointShadow = shadow;
                framebufferService.bind(context.currentPointShadow.getShadowMap());
                {
                    context.clippingPlane.set(0, 0, 0, 0);
                    context.viewport.set(context.currentPointShadow.getShadowMap().getSize());
                    clearScreenRenderService.clearScreen(ColorUtils.BLACK);
                    pointShadowRenderService.process(context);
                }
                framebufferService.unbind();
            }
        }
    }

    private void renderToWater(final RenderWorldPlanContext context) {
        context.currentCamera = context.playerCamera;

        float cameraHeight = context.currentCamera.getPosition().y;
        float cameraPitch = context.currentCamera.getRotation().y;
        for (Water water : context.waters) {
            float waterHeight = water.position.y;
            float distance = 2 * (cameraHeight - waterHeight);

            framebufferService.bind(water.reflectionBuffer);
            {
                context.currentCamera.translateRotate(0, -distance, 0, 0, -cameraPitch * 2, 0);

                context.clippingPlane.set(0, 1, 0, -waterHeight + 0.1f);
                context.viewport.set(water.reflectionBuffer.getSize());
                clearScreenRenderService.clearScreen(ColorUtils.BLACK);
                terrainRenderService.process(context);
                modelRenderService.process(context);
                animatedModelRenderService.process(context);
                skyboxRenderService.process(context);

                context.currentCamera.translateRotate(0, distance, 0, 0, cameraPitch * 2, 0);
            }
            framebufferService.unbind();

            framebufferService.bind(water.refractionBuffer);
            {
                context.clippingPlane.set(0, -1, 0, waterHeight + 1);
                context.viewport.set(water.refractionBuffer.getSize());
                clearScreenRenderService.clearScreen(ColorUtils.BLACK);
                terrainRenderService.process(context);
                modelRenderService.process(context);
                animatedModelRenderService.process(context);
                skyboxRenderService.process(context);
            }
            framebufferService.unbind();
        }
    }

    private void renderToWorld(final RenderWorldPlanContext context) {
        context.currentCamera = context.playerCamera;
        context.clippingPlane.set(0, 0, 0, 0);
        context.viewport.set(context.currentCamera.viewport);
        terrainRenderService.process(context);
        modelRenderService.process(context);
        animatedModelRenderService.process(context);
        skyboxRenderService.process(context);
        waterRenderService.process(context);
        particleSystemRenderService.process(context);
    }
}
