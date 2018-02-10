package piengine.visual.display.domain.glfw;

import org.lwjgl.glfw.GLFWKeyCallback;
import piengine.core.base.event.Event;
import piutils.map.ListMap;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

class KeyCallback extends GLFWKeyCallback {

    private final ListMap<Integer, Event> releaseEventMap;
    private final ListMap<Integer, Event> pressEventMap;

    KeyCallback(final ListMap<Integer, Event> releaseEventMap, final ListMap<Integer, Event> pressEventMap) {
        this.releaseEventMap = releaseEventMap;
        this.pressEventMap = pressEventMap;
    }

    @Override
    public void invoke(long window, int key, int scanCode, int action, int mods) {
        if (action == GLFW_RELEASE) {
            releaseEventMap.get(key).forEach(Event::invoke);
        } else if (action == GLFW_PRESS) {
            pressEventMap.get(key).forEach(Event::invoke);
        }
    }

}
