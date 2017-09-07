package piengine.core.input.interpreter;

import org.joml.Vector2f;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import piengine.core.base.event.Action;

import java.util.List;

public class CursorPosCallback extends GLFWCursorPosCallback {

    private static final Vector2f CURSOR_POSITION = new Vector2f();

    private final List<Action<Vector2f>> cursorEvents;

    public CursorPosCallback(final List<Action<Vector2f>> cursorEvents) {
        this.cursorEvents = cursorEvents;
    }

    @Override
    public void invoke(long window, double xPos, double yPos) {
        CURSOR_POSITION.set((float) xPos, (float) yPos);
        cursorEvents.forEach(event -> event.invoke(CURSOR_POSITION));
    }

}
