package piengine.object.water.domain;

import piengine.object.entity.domain.Entity;
import piengine.object.entity.domain.EntityDomain;
import piengine.visual.framebuffer.domain.Framebuffer;

public class Water extends EntityDomain<WaterDao> {

    public final Framebuffer reflectionBuffer;
    public final Framebuffer refractionBuffer;
    public float waveFactor = 0;

    public Water(final Entity parent, final WaterDao dao, final Framebuffer reflectionBuffer, final Framebuffer refractionBuffer) {
        super(parent, dao);

        this.reflectionBuffer = reflectionBuffer;
        this.refractionBuffer = refractionBuffer;
    }
}
