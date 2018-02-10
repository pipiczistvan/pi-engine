package piengine.visual.display.domain.glfw;

import org.joml.Vector2f;
import org.lwjgl.glfw.GLFWScrollCallback;
import piengine.core.base.event.Action;

import java.util.List;

class ScrollCallback extends GLFWScrollCallback {

    private static final Vector2f SCROLLING = new Vector2f();

    private final List<Action<Vector2f>> scrollEvents;

    ScrollCallback(final List<Action<Vector2f>> scrollEvents) {
        this.scrollEvents = scrollEvents;
    }

    @Override
    public void invoke(long windowId, double xoffset, double yoffset) {
        SCROLLING.set((float) xoffset, (float) yoffset);
        scrollEvents.forEach(event -> event.invoke(SCROLLING));
    }
}
