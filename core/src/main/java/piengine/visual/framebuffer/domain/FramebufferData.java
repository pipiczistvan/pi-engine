package piengine.visual.framebuffer.domain;

import org.joml.Vector2i;
import piengine.visual.texture.domain.Texture;
import piengine.visual.texture.domain.TextureData;

public class FramebufferData extends TextureData {

    public final FramebufferKey key;
    public final Vector2i resolution;
    public final Texture texture;
    public final boolean drawingEnabled;
    public final FramebufferAttachment textureAttachment;
    public final FramebufferAttachment[] attachments;

    public FramebufferData(final FramebufferKey key, final Vector2i resolution, final Texture texture, final boolean drawingEnabled, final FramebufferAttachment textureAttachment, final FramebufferAttachment[] attachments) {
        super(resolution.x, resolution.y, null);
        this.key = key;
        this.resolution = resolution;
        this.texture = texture;
        this.drawingEnabled = drawingEnabled;
        this.textureAttachment = textureAttachment;
        this.attachments = attachments;
    }
}
