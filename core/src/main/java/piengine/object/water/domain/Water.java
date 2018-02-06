package piengine.object.water.domain;

import org.joml.Vector3f;
import piengine.core.base.domain.Domain;
import piengine.core.base.type.color.Color;
import piengine.io.interpreter.framebuffer.Framebuffer;
import piengine.io.interpreter.vertexarray.VertexArray;

public class Water implements Domain {

    public final VertexArray vao;
    public final Framebuffer reflectionBuffer;
    public final Framebuffer refractionBuffer;
    public final Vector3f position;
    public final Vector3f rotation;
    public final Vector3f scale;
    public final Color color;
    public float waveFactor = 0;

    public Water(final VertexArray vao, final Framebuffer reflectionBuffer, final Framebuffer refractionBuffer, final Vector3f position, final Vector3f rotation, final Vector3f scale, final Color color) {
        this.vao = vao;
        this.reflectionBuffer = reflectionBuffer;
        this.refractionBuffer = refractionBuffer;
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
        this.color = color;
    }
}
