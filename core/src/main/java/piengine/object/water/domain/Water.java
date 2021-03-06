package piengine.object.water.domain;

import org.joml.Vector3f;
import piengine.core.base.domain.Domain;
import piengine.core.base.type.color.Color;
import piengine.visual.framebuffer.domain.Framebuffer;

public class Water implements Domain<WaterDao> {

    public final Framebuffer reflectionBuffer;
    public final Framebuffer refractionBuffer;
    public final Vector3f position;
    public final Vector3f rotation;
    public final Vector3f scale;
    public final Color color;
    private final WaterDao dao;
    public float waveFactor = 0;

    public Water(final WaterDao dao, final Framebuffer reflectionBuffer, final Framebuffer refractionBuffer, final Vector3f position, final Vector3f rotation, final Vector3f scale, final Color color) {
        this.dao = dao;
        this.reflectionBuffer = reflectionBuffer;
        this.refractionBuffer = refractionBuffer;
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
        this.color = color;
    }

    @Override
    public WaterDao getDao() {
        return dao;
    }
}
