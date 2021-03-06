package piengine.visual.render.interpreter;

import org.joml.Vector2i;
import piengine.core.base.type.color.Color;
import piengine.visual.render.domain.config.ProvokingVertex;
import puppeteer.annotation.premade.Component;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_FILL;
import static org.lwjgl.opengl.GL11.GL_FRONT_AND_BACK;
import static org.lwjgl.opengl.GL11.GL_LINE;
import static org.lwjgl.opengl.GL11.GL_NONE;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glCullFace;
import static org.lwjgl.opengl.GL11.glDepthMask;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glPolygonMode;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL30.GL_CLIP_DISTANCE0;
import static org.lwjgl.opengl.GL32.glProvokingVertex;

@Component
public class RenderInterpreter {

    public void setClearColor(final Color color) {
        glClearColor(color.r, color.g, color.b, color.a);
    }

    public void clearScreen() {
        clear(GL_COLOR_BUFFER_BIT, GL_DEPTH_BUFFER_BIT);
    }

    public void setViewport(final Vector2i viewport) {
        glViewport(0, 0, viewport.x, viewport.y);
    }

    public void setProvokingVertex(final ProvokingVertex provokingVertex) {
        glProvokingVertex(provokingVertex.value);
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

    public void setDepthMask(final boolean enabled) {
        glDepthMask(enabled);
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

    public void setClipDistance(final boolean enabled) {
        if (enabled) {
            glEnable(GL_CLIP_DISTANCE0);
        } else {
            glDisable(GL_CLIP_DISTANCE0);
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
