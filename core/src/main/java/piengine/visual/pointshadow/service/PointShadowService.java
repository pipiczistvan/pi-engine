package piengine.visual.pointshadow.service;

import piengine.core.base.resource.SupplierService;
import piengine.visual.cubemap.domain.CubeMap;
import piengine.visual.cubemap.manager.CubeMapManager;
import piengine.visual.framebuffer.domain.Framebuffer;
import piengine.visual.framebuffer.manager.FramebufferManager;
import piengine.visual.pointshadow.accessor.PointShadowAccessor;
import piengine.visual.pointshadow.domain.PointShadow;
import piengine.visual.pointshadow.domain.PointShadowDao;
import piengine.visual.pointshadow.domain.PointShadowData;
import piengine.visual.pointshadow.domain.PointShadowKey;
import piengine.visual.pointshadow.interpreter.PointShadowInterpreter;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import static org.lwjgl.opengl.GL11.GL_DEPTH_COMPONENT;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static piengine.visual.framebuffer.domain.FramebufferAttachment.DEPTH_TEXTURE_ATTACHMENT;

@Component
public class PointShadowService extends SupplierService<PointShadowKey, PointShadowData, PointShadowDao, PointShadow> {

    private final FramebufferManager framebufferManager;
    private final CubeMapManager cubeMapManager;

    @Wire
    public PointShadowService(final PointShadowAccessor pointShadowAccessor, final PointShadowInterpreter pointShadowInterpreter,
                              final FramebufferManager framebufferManager, final CubeMapManager cubeMapManager) {
        super(pointShadowAccessor, pointShadowInterpreter);

        this.framebufferManager = framebufferManager;
        this.cubeMapManager = cubeMapManager;
    }

    @Override
    protected PointShadow createDomain(final PointShadowDao dao, final PointShadowData resource) {
        CubeMap shadowCubeMap = cubeMapManager.supply(GL_DEPTH_COMPONENT, GL_FLOAT, resource.resolution);
        Framebuffer shadowMap = framebufferManager.supply(resource.resolution, shadowCubeMap, false, DEPTH_TEXTURE_ATTACHMENT);

        return new PointShadow(dao, resource.light, shadowMap, resource.resolution);
    }
}
