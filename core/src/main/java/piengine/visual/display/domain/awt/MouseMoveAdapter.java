package piengine.visual.display.domain.awt;

import org.joml.Vector2f;
import piengine.core.base.event.Action;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.List;

public class MouseMoveAdapter extends MouseMotionAdapter {

    private static final Vector2f CURSOR_POSITION = new Vector2f();

    private final List<Action<Vector2f>> cursorEvents;

    MouseMoveAdapter(final List<Action<Vector2f>> cursorEvents) {
        this.cursorEvents = cursorEvents;
    }

    @Override
    public void mouseMoved(final MouseEvent e) {
        if (e.getComponent().isFocusOwner()) {
            CURSOR_POSITION.set((float) e.getXOnScreen(), (float) e.getYOnScreen());
            cursorEvents.forEach(event -> event.invoke(CURSOR_POSITION));
        }
    }
}
