package piengine.visual.display.domain.awt;

import piengine.core.base.event.Event;
import piutils.map.ListMap;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class KeyAdapter implements KeyListener {

    private final ListMap<Integer, Event> releaseEventMap;
    private final ListMap<Integer, Event> pressEventMap;

    KeyAdapter(final ListMap<Integer, Event> releaseEventMap, final ListMap<Integer, Event> pressEventMap) {
        this.releaseEventMap = releaseEventMap;
        this.pressEventMap = pressEventMap;
    }

    @Override
    public void keyPressed(final KeyEvent e) {
        pressEventMap.get(e.getKeyCode()).forEach(Event::invoke);
    }

    @Override
    public void keyReleased(final KeyEvent e) {
        releaseEventMap.get(e.getKeyCode()).forEach(Event::invoke);
    }

    @Override
    public void keyTyped(final KeyEvent e) {
    }
}
