package piengine.visual.display.domain.awt;

import piengine.core.base.event.Event;
import piutils.map.ListMap;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

class KeyAdapter implements KeyListener {

    private final ListMap<Integer, Event> releaseEventMap;
    private final ListMap<Integer, Event> pressEventMap;

    private final Map<Integer, Boolean> pressedMap = new HashMap<>();

    KeyAdapter(final ListMap<Integer, Event> releaseEventMap, final ListMap<Integer, Event> pressEventMap) {
        this.releaseEventMap = releaseEventMap;
        this.pressEventMap = pressEventMap;
    }

    @Override
    public void keyPressed(final KeyEvent e) {
        if (!pressedMap.containsKey(e.getKeyCode()) || !pressedMap.get(e.getKeyCode())) {
            pressEventMap.get(e.getKeyCode()).forEach(Event::invoke);
        }
        pressedMap.put(e.getKeyCode(), true);
    }

    @Override
    public void keyReleased(final KeyEvent e) {
        if (!pressedMap.containsKey(e.getKeyCode()) || pressedMap.get(e.getKeyCode())) {
            releaseEventMap.get(e.getKeyCode()).forEach(Event::invoke);
        }
        pressedMap.put(e.getKeyCode(), false);
    }

    @Override
    public void keyTyped(final KeyEvent e) {
    }
}
