package piengine.visual.display.domain.awt;

import piengine.core.base.event.Event;
import piutils.map.ListMap;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseButtonAdapter extends MouseAdapter {

    private final ListMap<Integer, Event> releaseEventMap;
    private final ListMap<Integer, Event> pressEventMap;

    MouseButtonAdapter(final ListMap<Integer, Event> releaseEventMap, final ListMap<Integer, Event> pressEventMap) {
        this.releaseEventMap = releaseEventMap;
        this.pressEventMap = pressEventMap;
    }

    @Override
    public void mousePressed(final MouseEvent e) {
        pressEventMap.get(e.getButton()).forEach(Event::invoke);
    }

    @Override
    public void mouseReleased(final MouseEvent e) {
        releaseEventMap.get(e.getButton()).forEach(Event::invoke);
    }
}
