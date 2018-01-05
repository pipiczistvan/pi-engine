package piengine.visual.render.domain.config;

import static org.lwjgl.opengl.GL32.GL_FIRST_VERTEX_CONVENTION;
import static org.lwjgl.opengl.GL32.GL_LAST_VERTEX_CONVENTION;

public enum ProvokingVertex {
    FIRST_VERTEX_CONVENTION(GL_FIRST_VERTEX_CONVENTION),
    LAST_VERTEX_CONVENTION(GL_LAST_VERTEX_CONVENTION);

    public final int value;

    ProvokingVertex(final int value) {
        this.value = value;
    }
}
