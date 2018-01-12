package piengine.visual.framebuffer.manager;

import piengine.visual.framebuffer.domain.Framebuffer;
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

    public Framebuffer supply(final FramebufferKey key) {
        return framebufferService.supply(key);
    }
}
