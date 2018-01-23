package piengine.visual.framebuffer.domain;

import org.joml.Vector2i;
import piengine.core.base.domain.Key;
import piengine.visual.texture.domain.Texture;

public class FramebufferKey implements Key {

    public final Vector2i resolution;
    public final Texture texture;
    public final boolean drawingEnabled;
    public final FramebufferAttachment[] attachments;

    public FramebufferKey(final Vector2i resolution, final Texture texture, final boolean drawingEnabled, final FramebufferAttachment textureAttachment, final FramebufferAttachment... attachments) {
        this.resolution = resolution;
        this.texture = texture;
        this.drawingEnabled = drawingEnabled;

        this.attachments = new FramebufferAttachment[attachments.length + 1];
        this.attachments[0] = textureAttachment;
        for (int i = 0; i < attachments.length; i++) {
            this.attachments[i + 1] = attachments[i];
        }
    }
}