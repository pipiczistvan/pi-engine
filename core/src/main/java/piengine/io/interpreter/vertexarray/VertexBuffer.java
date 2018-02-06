package piengine.io.interpreter.vertexarray;

import piengine.io.interpreter.Interpreter;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_INT;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.GL_STREAM_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glBufferSubData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glVertexAttribIPointer;
import static org.lwjgl.opengl.GL33.glVertexAttribDivisor;

public class VertexBuffer implements Interpreter {

    private static final int FLOAT_SIZE = 4;

    private final int id;

    VertexBuffer() {
        this.id = glGenBuffers();
    }

    public VertexBuffer bind() {
        glBindBuffer(GL_ARRAY_BUFFER, id);

        return this;
    }

    public VertexBuffer unbind() {
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        return this;
    }

    @Override
    public void clear() {
        glDeleteBuffers(id);
    }

    public VertexBuffer attachIndexBuffer(final IntBuffer buffer) {
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);

        return this;
    }

    public VertexBuffer attachFloatBuffer(final VertexAttribute attribute, final FloatBuffer buffer, final int size) {
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
        glVertexAttribPointer(attribute.index, size, GL_FLOAT, false, 0, 0);

        return this;
    }

    public VertexBuffer attachFloatBuffer(final int floatCount) {
        glBufferData(GL_ARRAY_BUFFER, floatCount * 4, GL_STREAM_DRAW);

        return this;
    }

    public VertexBuffer attachIntBuffer(final VertexAttribute attribute, final IntBuffer buffer, final int size) {
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
        glVertexAttribIPointer(attribute.index, size, GL_INT, 0, 0);

        return this;
    }

    public VertexBuffer attachAttribute(final VertexAttribute attribute, final int dataSize, final int instancedDataLength, final int offset) {
        glVertexAttribPointer(attribute.index, dataSize, GL_FLOAT, false, instancedDataLength * FLOAT_SIZE, offset * FLOAT_SIZE);
        glVertexAttribDivisor(attribute.index, 1);

        return this;
    }

    public VertexBuffer update(final float[] data, final FloatBuffer buffer) {
        buffer.clear();
        buffer.put(data);
        buffer.flip();
        glBufferData(GL_ARRAY_BUFFER, buffer.capacity() * FLOAT_SIZE, GL_STREAM_DRAW);
        glBufferSubData(GL_ARRAY_BUFFER, 0, buffer);

        return this;
    }
}
