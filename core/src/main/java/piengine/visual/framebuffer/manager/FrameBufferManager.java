package piengine.visual.framebuffer.manager;

import piengine.visual.framebuffer.domain.FrameBuffer;
import piengine.visual.framebuffer.domain.FrameBufferData;
import piengine.visual.framebuffer.service.FrameBufferService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

@Component
public class FrameBufferManager {

    private final FrameBufferService frameBufferService;

    @Wire
    public FrameBufferManager(final FrameBufferService frameBufferService) {
        this.frameBufferService = frameBufferService;
    }

    public FrameBuffer supply(final FrameBufferData frameBufferData) {
        return frameBufferService.supply(frameBufferData);
    }
}
