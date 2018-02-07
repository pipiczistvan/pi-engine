package piengine.visual.framebuffer.domain;

import org.joml.Vector2i;
import piengine.core.base.domain.Key;
import piengine.visual.texture.domain.Texture;

public class FramebufferKey implements Key {

    public final Vector2i resolution;
    public final Texture texture;
    public final boolean drawingEnabled;
    public final boolean fixed;
    public final FramebufferAttachment[] attachments;

    public FramebufferKey(final Vector2i resolution, final Texture texture, final boolean drawingEnabled,
                          final boolean fixed, final FramebufferAttachment textureAttachment, final FramebufferAttachment... attachments) {
        this.resolution = resolution;
        this.texture = texture;
        this.drawingEnabled = drawingEnabled;
        this.fixed = fixed;

        this.attachments = new FramebufferAttachment[attachments.length + 1];
        this.attachments[0] = textureAttachment;
        System.arraycopy(attachments, 0, this.attachments, 1, attachments.length);
    }
}
