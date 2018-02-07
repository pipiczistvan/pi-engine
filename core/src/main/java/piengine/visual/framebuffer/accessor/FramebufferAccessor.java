package piengine.visual.framebuffer.accessor;

import piengine.core.base.api.Accessor;
import piengine.visual.framebuffer.domain.FramebufferData;
import piengine.visual.framebuffer.domain.FramebufferKey;
import puppeteer.annotation.premade.Component;

@Component
public class FramebufferAccessor extends Accessor<FramebufferKey, FramebufferData> {

    @Override
    protected FramebufferData accessResource(final FramebufferKey key) {
        return new FramebufferData(key.resolution, key.texture, key.drawingEnabled, key.fixed, key.attachments[0], key.attachments);
    }

    @Override
    protected String getAccessInfo(final FramebufferKey key, final FramebufferData resource) {
        return String.format("Resolution: %s", key.resolution);
    }
}
