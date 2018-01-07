package piengine.visual.framebuffer.domain;

import org.joml.Vector2i;
import piengine.visual.texture.domain.Texture;

public class FrameBuffer extends Texture<FrameBufferDao> {

    public final Vector2i resolution;

    public FrameBuffer(final FrameBufferDao dao, final Vector2i resolution) {
        super(dao);

        this.resolution = resolution;
    }
}
