package piengine.visual.framebuffer.domain;

import org.joml.Vector2i;
import piengine.core.base.domain.ResourceData;

public class FrameBufferData implements ResourceData {

    public final Vector2i size;
    public final FrameBufferAttachment[] attachments;

    public FrameBufferData(final Vector2i size, final FrameBufferAttachment... attachments) {
        this.size = size;
        this.attachments = attachments;
    }
}
