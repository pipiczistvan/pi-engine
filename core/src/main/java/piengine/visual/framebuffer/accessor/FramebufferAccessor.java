package piengine.visual.framebuffer.accessor;

import piengine.core.base.api.Accessor;
import piengine.visual.framebuffer.domain.FramebufferData;
import piengine.visual.framebuffer.domain.FramebufferKey;
import puppeteer.annotation.premade.Component;

@Component
public class FramebufferAccessor implements Accessor<FramebufferKey, FramebufferData> {

    @Override
    public FramebufferData access(final FramebufferKey key) {
        return new FramebufferData(key, key.resolution, key.texture, key.drawingEnabled, key.attachments[0], key.attachments);
    }
}
