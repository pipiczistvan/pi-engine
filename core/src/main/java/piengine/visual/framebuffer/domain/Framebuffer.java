package piengine.visual.framebuffer.domain;

import org.joml.Vector2i;
import piengine.visual.texture.domain.Texture;

public class Framebuffer extends Texture<FramebufferDao> {

    private final boolean fixed;

    public Framebuffer(final FramebufferDao dao, final Vector2i resolution, final boolean fixed) {
        super(dao, resolution);

        this.fixed = fixed;
    }

    public boolean isFixed() {
        return fixed;
    }

    public void setDao(final FramebufferDao dao) {
        this.dao = dao;
    }

    public void setSize(final Vector2i size) {
        this.size.set(size);
    }
}
