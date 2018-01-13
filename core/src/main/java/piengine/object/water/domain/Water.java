package piengine.object.water.domain;

import org.joml.Vector3f;
import piengine.core.base.domain.Domain;
import piengine.visual.framebuffer.domain.Framebuffer;

public class Water implements Domain<WaterDao> {

    private final WaterDao dao;

    public final Framebuffer reflectionBuffer;
    public final Framebuffer refractionBuffer;
    public final Vector3f position;
    public final Vector3f rotation;
    public final Vector3f scale;

    public float waveFactor = 0;

    public Water(final WaterDao dao, final Framebuffer reflectionBuffer, final Framebuffer refractionBuffer, final Vector3f position, final Vector3f rotation, final Vector3f scale) {
        this.dao = dao;
        this.reflectionBuffer = reflectionBuffer;
        this.refractionBuffer = refractionBuffer;
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
    }

    @Override
    public WaterDao getDao() {
        return dao;
    }
}
