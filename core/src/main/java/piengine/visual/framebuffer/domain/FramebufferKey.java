package piengine.visual.framebuffer.domain;

import org.joml.Vector2i;

import java.util.Arrays;
import java.util.Objects;

public class FramebufferKey {

    public final Vector2i resolution;
    public final boolean drawingEnabled;
    public final FramebufferAttachment[] attachments;

    public FramebufferKey(final Vector2i resolution, final FramebufferAttachment textureAttachment, final FramebufferAttachment... attachments) {
        this(resolution, true, textureAttachment, attachments);
    }

    public FramebufferKey(final Vector2i resolution, final boolean drawingEnabled, final FramebufferAttachment textureAttachment, final FramebufferAttachment... attachments) {
        this.resolution = resolution;
        this.drawingEnabled = drawingEnabled;

        this.attachments = new FramebufferAttachment[attachments.length + 1];
        this.attachments[0] = textureAttachment;
        for (int i = 0; i < attachments.length; i++) {
            this.attachments[i + 1] = attachments[i];
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FramebufferKey that = (FramebufferKey) o;
        return drawingEnabled == that.drawingEnabled &&
                Objects.equals(resolution, that.resolution) &&
                Arrays.equals(attachments, that.attachments);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(resolution, drawingEnabled);
        result = 31 * result + Arrays.hashCode(attachments);
        return result;
    }
}
