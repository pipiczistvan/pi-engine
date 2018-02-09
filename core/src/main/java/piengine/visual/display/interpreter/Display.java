package piengine.visual.display.interpreter;

import org.joml.Vector2f;
import piengine.core.base.api.Initializable;
import piengine.core.base.api.Terminatable;
import piengine.core.base.api.Updatable;

public abstract class Display implements Initializable, Updatable, Terminatable {

    protected abstract void render();

    protected abstract boolean isResized();

    protected abstract void resize();

    protected abstract boolean shouldUpdate();

    protected abstract Vector2f getPointer();

    protected abstract void setPointer(Vector2f position);

    protected abstract Vector2f getDisplayCenter();

    protected abstract void closeDisplay();

    protected abstract int getWidth();

    protected abstract int getHeight();

    protected abstract int getOldWidth();

    protected abstract int getOldHeight();

    protected abstract void setCursorVisibility(final boolean visible);
}
