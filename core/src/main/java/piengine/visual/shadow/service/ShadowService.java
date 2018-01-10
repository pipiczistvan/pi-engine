package piengine.visual.shadow.service;

import org.joml.Vector2i;
import piengine.core.base.api.Updatable;
import piengine.core.base.resource.SupplierService;
import piengine.visual.framebuffer.domain.FrameBuffer;
import piengine.visual.framebuffer.domain.FrameBufferData;
import piengine.visual.framebuffer.service.FrameBufferService;
import piengine.visual.shadow.accessor.ShadowAccessor;
import piengine.visual.shadow.domain.Shadow;
import piengine.visual.shadow.domain.ShadowDao;
import piengine.visual.shadow.domain.ShadowData;
import piengine.visual.shadow.domain.ShadowKey;
import piengine.visual.shadow.interpreter.ShadowInterpreter;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import static org.lwjgl.opengl.GL11.GL_NONE;
import static org.lwjgl.opengl.GL30.GL_COLOR_ATTACHMENT0;
import static piengine.visual.framebuffer.domain.FrameBufferAttachment.COLOR_ATTACHMENT;
import static piengine.visual.framebuffer.domain.FrameBufferAttachment.DEPTH_TEXTURE_ATTACHMENT;
import static piengine.visual.framebuffer.domain.FrameBufferAttachment.RENDER_BUFFER_ATTACHMENT;

@Component
public class ShadowService extends SupplierService<ShadowKey, ShadowData, ShadowDao, Shadow> implements Updatable {

    private final FrameBufferService frameBufferService;

    @Wire
    public ShadowService(final ShadowAccessor shadowAccessor, final ShadowInterpreter shadowInterpreter,
                         final FrameBufferService frameBufferService) {
        super(shadowAccessor, shadowInterpreter);

        this.frameBufferService = frameBufferService;
    }

    @Override
    protected Shadow createDomain(final ShadowDao dao, final ShadowData resource) {
        FrameBuffer shadowMap1 = frameBufferService.supply(new FrameBufferData(
                new Vector2i(resource.resolution),
                COLOR_ATTACHMENT,
                GL_COLOR_ATTACHMENT0,
                COLOR_ATTACHMENT,
                RENDER_BUFFER_ATTACHMENT
        ));
        FrameBuffer shadowMap2 = frameBufferService.supply(new FrameBufferData(
                new Vector2i(resource.resolution),
                DEPTH_TEXTURE_ATTACHMENT,
                GL_NONE,
                DEPTH_TEXTURE_ATTACHMENT
        ));

        return new Shadow(dao, resource.playerCamera, resource.light, shadowMap2);
    }

    @Override
    public void update(double delta) {
        for (Shadow shadow : getValues()) {
            shadow.update(delta);
        }
    }
}
