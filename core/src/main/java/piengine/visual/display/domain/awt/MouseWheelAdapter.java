package piengine.visual.display.domain.awt;

import org.joml.Vector2f;
import piengine.core.base.event.Action;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.List;

class MouseWheelAdapter implements MouseWheelListener {

    private static final Vector2f SCROLLING = new Vector2f();

    private final List<Action<Vector2f>> scrollEvents;

    MouseWheelAdapter(final List<Action<Vector2f>> scrollEvents) {
        this.scrollEvents = scrollEvents;
    }

    @Override
    public void mouseWheelMoved(final MouseWheelEvent e) {
        SCROLLING.set(0, -e.getWheelRotation());
        scrollEvents.forEach(event -> event.invoke(SCROLLING));
    }
}
