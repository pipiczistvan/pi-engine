package piengine.core.opengl.vertexarray;

import piengine.core.opengl.OpenglObject;
import piengine.core.opengl.vertexarray.vertexbuffer.VertexBuffer;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static piengine.core.opengl.vertexarray.VertexAttribute.INDEX;
import static piengine.core.utils.BufferUtils.convertToFloatBuffer;
import static piengine.core.utils.BufferUtils.convertToIntBuffer;

public class VertexArray implements OpenglObject {

    public final int id;
    public final int vertexCount;
    public final Map<VertexAttribute, VertexBuffer> vertexBuffers;

    public VertexArray(final int vertexCount) {
        this.id = glGenVertexArrays();
        this.vertexCount = vertexCount;
        this.vertexBuffers = new HashMap<>();
    }

    public VertexArray bind() {
        glBindVertexArray(id);

        return this;
    }

    public VertexArray unbind() {
        glBindVertexArray(0);

        return this;
    }

    @Override
    public void clear() {
        unbind();
        glDeleteVertexArrays(id);
        vertexBuffers.values().forEach(VertexBuffer::clear);
    }

    public VertexArray attachIndexBuffer(final int[] data) {
        IntBuffer buffer = convertToIntBuffer(data);

        vertexBuffers.put(INDEX, new VertexBuffer()
                .bind()
                .attachIndexBuffer(buffer)
        );

        return this;
    }

    public VertexArray attachVertexBuffer(final VertexAttribute attribute, final float[] data, final int size) {
        FloatBuffer buffer = convertToFloatBuffer(data);

        return attachVertexBuffer(attribute, createVertexBuffer()
                .bind()
                .attachFloatBuffer(attribute, buffer, size)
                .unbind()
        );
    }

    public VertexArray attachVertexBuffer(final VertexAttribute attribute, final int[] data, final int size) {
        IntBuffer buffer = convertToIntBuffer(data);

        return attachVertexBuffer(attribute, createVertexBuffer()
                .bind()
                .attachIntBuffer(attribute, buffer, size)
                .unbind()
        );
    }

    public VertexArray attachVertexBuffer(final VertexAttribute attribute, final VertexBuffer vertexBuffer) {
        vertexBuffers.put(attribute, vertexBuffer);

        return this;
    }

    public VertexBuffer createVertexBuffer() {
        return new VertexBuffer();
    }

    public VertexArray enableAttributes() {
        vertexBuffers.keySet().stream()
                .filter(attribute -> attribute.index >= 0)
                .forEach(attribute -> glEnableVertexAttribArray(attribute.index));

        return this;
    }

    public VertexArray disableAttributes() {
        vertexBuffers.keySet().stream()
                .filter(attribute -> attribute.index >= 0)
                .forEach(attribute -> glDisableVertexAttribArray(attribute.index));

        return this;
    }
}
