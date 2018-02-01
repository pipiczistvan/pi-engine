package piengine.visual.framebuffer.domain;

import org.joml.Vector2i;
import piengine.visual.texture.domain.Texture;

public class Framebuffer extends Texture<FramebufferDao> {

    public final FramebufferKey key;

    public Framebuffer(final FramebufferDao dao, final FramebufferKey key, final Vector2i resolution) {
        super(dao, resolution);

        this.key = key;
    }

    public void setDao(final FramebufferDao dao) {
        this.dao = dao;
    }

    public void setSize(final Vector2i size) {
        this.size.set(size);
    }
}
