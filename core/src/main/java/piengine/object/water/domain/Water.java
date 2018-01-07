package piengine.object.water.domain;

import piengine.object.entity.domain.Entity;
import piengine.object.entity.domain.EntityDomain;
import piengine.visual.framebuffer.domain.FrameBuffer;

public class Water extends EntityDomain<WaterDao> {

    public final FrameBuffer reflectionBuffer;
    public final FrameBuffer refractionBuffer;
    public float waveFactor = 0;

    public Water(final Entity parent, final WaterDao dao, final FrameBuffer reflectionBuffer, final FrameBuffer refractionBuffer) {
        super(parent, dao);

        this.reflectionBuffer = reflectionBuffer;
        this.refractionBuffer = refractionBuffer;
    }
}
