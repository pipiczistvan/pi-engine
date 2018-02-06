package piengine.object.animatedmodel.domain;

import piengine.core.base.domain.Entity;
import piengine.core.base.domain.Key;
import piengine.io.interpreter.texture.Texture;

public class AnimatedModelKey implements Key {

    public final Entity parent;
    public final String colladaFile;
    public final Texture texture;

    public AnimatedModelKey(final Entity parent, final String colladaFile, final Texture texture) {
        this.parent = parent;
        this.colladaFile = colladaFile;
        this.texture = texture;
    }
}
