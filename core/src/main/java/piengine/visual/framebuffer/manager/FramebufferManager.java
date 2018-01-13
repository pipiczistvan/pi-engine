package piengine.visual.framebuffer.manager;

import org.joml.Vector2i;
import piengine.visual.framebuffer.domain.Framebuffer;
import piengine.visual.framebuffer.domain.FramebufferAttachment;
import piengine.visual.framebuffer.domain.FramebufferKey;
import piengine.visual.framebuffer.service.FramebufferService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

@Component
public class FramebufferManager {

    private final FramebufferService framebufferService;

    @Wire
    public FramebufferManager(final FramebufferService framebufferService) {
        this.framebufferService = framebufferService;
    }

    public Framebuffer supply(final Vector2i resolution, final boolean drawingEnabled, final FramebufferAttachment textureAttachment, final FramebufferAttachment... attachments) {
        return framebufferService.supply(new FramebufferKey(resolution, drawingEnabled, textureAttachment, attachments));
    }

    public Framebuffer supply(final Vector2i resolution, final FramebufferAttachment textureAttachment, final FramebufferAttachment... attachments) {
        return supply(resolution, true, textureAttachment, attachments);
    }
}
