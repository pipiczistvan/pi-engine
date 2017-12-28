package piengine.visual.render.interpreter;

import org.joml.Vector2i;
import org.joml.Vector4f;
import piengine.object.mesh.domain.MeshDataType;
import puppeteer.annotation.premade.Component;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

@Component
public class RenderInterpreter {

    public void setClearColor(final Vector4f color) {
        glClearColor(color.x, color.y, color.z, color.w);
    }

    public void clearScreen() {
        clear(GL_COLOR_BUFFER_BIT, GL_DEPTH_BUFFER_BIT);
    }

    public void setViewport(final Vector2i viewport) {
        glViewport(0, 0, viewport.x, viewport.y);
    }

    public void bindVertexArray(final int id) {
        glBindVertexArray(id);
    }

    public void unbindVertexArray() {
        bindVertexArray(0);
    }

    public void enableVertexAttribArray(MeshDataType... types) {
        for (MeshDataType type : types) {
            glEnableVertexAttribArray(type.value);
        }
    }

    public void disableVertexAttribArray(MeshDataType... types) {
        for (MeshDataType type : types) {
            glDisableVertexAttribArray(type.value);
        }
    }

    public void drawArrays(int mode, int count) {
        glDrawArrays(mode, 0, count);
    }

    public void drawElements(int mode, int count) {
        glDrawElements(mode, count, GL_UNSIGNED_INT, 0);
    }

    public void setDepthTest(final boolean enabled) {
        if (enabled) {
            glEnable(GL_DEPTH_TEST);
        } else {
            glDisable(GL_DEPTH_TEST);
        }
    }

    public void setBlendTest(final boolean enabled) {
        if (enabled) {
            glEnable(GL_BLEND);
            glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        } else {
            glDisable(GL_BLEND);
        }
    }

    public void setCullFace(final int mode) {
        if (mode == GL_NONE) {
            glDisable(GL_CULL_FACE);
        } else {
            glEnable(GL_CULL_FACE);
            glCullFace(mode);
        }
    }

    public void setWireFrameMode(final boolean wireFrameMode) {
        if (wireFrameMode) {
            glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
        } else {
            glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
        }
    }

    private void clear(final int... bits) {
        int mask = 0;
        for (int bit : bits) {
            mask |= bit;
        }
        glClear(mask);
    }

}
