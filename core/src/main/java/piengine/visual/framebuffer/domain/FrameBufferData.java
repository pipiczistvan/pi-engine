package piengine.visual.framebuffer.domain;

import org.joml.Vector2i;
import piengine.core.base.domain.ResourceData;

import static org.lwjgl.opengl.GL30.GL_COLOR_ATTACHMENT0;
import static piengine.visual.framebuffer.domain.FrameBufferAttachment.COLOR_ATTACHMENT;

public class FrameBufferData implements ResourceData {

    public final Vector2i size;
    public final int colorAttachment;
    public final FrameBufferAttachment[] attachments;
    public final FrameBufferAttachment textureAttachment;

    public FrameBufferData(final Vector2i size, final FrameBufferAttachment... attachments) {
        this(size, COLOR_ATTACHMENT, GL_COLOR_ATTACHMENT0, attachments);
    }

    public FrameBufferData(final Vector2i size, final FrameBufferAttachment textureAttachment, final int colorAttachment, final FrameBufferAttachment... attachments) {
        this.size = size;
        this.colorAttachment = colorAttachment;
        this.textureAttachment = textureAttachment;
        this.attachments = attachments;
    }
}
