package piengine.core.input.domain;

import static java.awt.event.KeyEvent.VK_A;
import static java.awt.event.KeyEvent.VK_CONTROL;
import static java.awt.event.KeyEvent.VK_D;
import static java.awt.event.KeyEvent.VK_ESCAPE;
import static java.awt.event.KeyEvent.VK_R;
import static java.awt.event.KeyEvent.VK_S;
import static java.awt.event.KeyEvent.VK_SHIFT;
import static java.awt.event.KeyEvent.VK_SPACE;
import static java.awt.event.KeyEvent.VK_W;
import static java.awt.event.MouseEvent.BUTTON1;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_SHIFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_R;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT_CONTROL;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_1;

public enum Key {
    KEY_A(GLFW_KEY_A, VK_A),
    KEY_D(GLFW_KEY_D, VK_D),
    KEY_S(GLFW_KEY_S, VK_S),
    KEY_R(GLFW_KEY_R, VK_R),
    KEY_W(GLFW_KEY_W, VK_W),
    KEY_LEFT_SHIFT(GLFW_KEY_LEFT_SHIFT, VK_SHIFT),
    KEY_SPACE(GLFW_KEY_SPACE, VK_SPACE),
    KEY_RIGHT_CONTROL(GLFW_KEY_RIGHT_CONTROL, VK_CONTROL),
    KEY_ESCAPE(GLFW_KEY_ESCAPE, VK_ESCAPE),
    MOUSE_BUTTON_1(GLFW_MOUSE_BUTTON_1, BUTTON1);

    private final int glfw;
    private final int awt;

    Key(final int glfw, final int awt) {
        this.glfw = glfw;
        this.awt = awt;
    }

    public int getGlfwCode() {
        return glfw;
    }

    public int getAwtCode() {
        return awt;
    }
}
