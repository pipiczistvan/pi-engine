package piengine.visual.lighting.directional.light.domain;

import piengine.core.base.domain.Entity;
import piengine.core.base.type.color.Color;
import piengine.visual.lighting.Light;
import piengine.visual.lighting.directional.shadow.domain.DirectionalShadow;

public class DirectionalLight extends Light<DirectionalShadow> {

    public DirectionalLight(final Entity parent, final Color color, final DirectionalShadow shadow) {
        super(parent, color, shadow);
    }
}
