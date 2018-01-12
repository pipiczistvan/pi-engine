package piengine.visual.framebuffer.domain;

import org.joml.Vector2i;
import piengine.visual.texture.domain.Texture;

public class Framebuffer extends Texture<FramebufferDao> {

    public final Vector2i resolution;

    public Framebuffer(final FramebufferDao dao, final Vector2i resolution) {
        super(dao);

        this.resolution = resolution;
    }
}
