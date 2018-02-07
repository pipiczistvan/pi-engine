package piengine.visual.framebuffer.domain;

import org.joml.Vector2i;
import piengine.visual.texture.domain.Texture;
import piengine.visual.texture.domain.TextureData;

public class FramebufferData extends TextureData {

    public final Vector2i resolution;
    public final Texture texture;
    public final boolean drawingEnabled;
    public final boolean fixed;
    public final FramebufferAttachment textureAttachment;
    public final FramebufferAttachment[] attachments;

    public FramebufferData(final Vector2i resolution, final Texture texture, final boolean drawingEnabled,
                           final boolean fixed, final FramebufferAttachment textureAttachment, final FramebufferAttachment[] attachments) {
        super(resolution.x, resolution.y, null);
        this.resolution = resolution;
        this.texture = texture;
        this.drawingEnabled = drawingEnabled;
        this.fixed = fixed;
        this.textureAttachment = textureAttachment;
        this.attachments = attachments;
    }
}
