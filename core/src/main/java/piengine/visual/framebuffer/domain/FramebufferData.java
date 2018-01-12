package piengine.visual.framebuffer.domain;

import org.joml.Vector2i;
import piengine.core.base.domain.ResourceData;

public class FramebufferData implements ResourceData {

    public final Vector2i resolution;
    public final boolean drawingEnabled;
    public final FramebufferAttachment textureAttachment;
    public final FramebufferAttachment[] attachments;

    public FramebufferData(final Vector2i resolution, final boolean drawingEnabled, final FramebufferAttachment textureAttachment, final FramebufferAttachment[] attachments) {
        this.resolution = resolution;
        this.drawingEnabled = drawingEnabled;
        this.textureAttachment = textureAttachment;
        this.attachments = attachments;
    }
}
