package piengine.core.input.interpreter;

import org.lwjgl.glfw.GLFWMouseButtonCallback;
import piengine.core.base.event.Event;
import piutils.map.ListMap;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class MouseButtonCallback extends GLFWMouseButtonCallback {

    private final ListMap<Integer, Event> releaseEventMap;
    private final ListMap<Integer, Event> pressEventMap;

    public MouseButtonCallback(final ListMap<Integer, Event> releaseEventMap, final ListMap<Integer, Event> pressEventMap) {
        this.releaseEventMap = releaseEventMap;
        this.pressEventMap = pressEventMap;
    }

    @Override
    public void invoke(long window, int button, int action, int mods) {
        if (action == GLFW_RELEASE) {
            releaseEventMap.get(button).forEach(Event::invoke);
        } else if (action == GLFW_PRESS) {
            pressEventMap.get(button).forEach(Event::invoke);
        }
    }

}
