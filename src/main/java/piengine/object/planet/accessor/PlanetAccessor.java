package piengine.object.planet.accessor;

import piengine.core.base.api.Accessor;
import piengine.object.planet.domain.PlanetData;
import puppeteer.annotation.premade.Component;

@Component
public class PlanetAccessor implements Accessor<PlanetData> {

    private static final float X = 0.525731112119133696f;
    private static final float Z = 0.850650808352039932f;

    private static float vertices[] = {
            -X, 0.0f, Z,
            X, 0.0f, Z,
            -X, 0.0f, -Z,
            X, 0.0f, -Z,
            0.0f, Z, X,
            0.0f, Z, -X,
            0.0f, -Z, X,
            0.0f, -Z, -X,
            Z, X, 0.0f,
            -Z, X, 0.0f,
            Z, -X, 0.0f,
            -Z, -X, 0.0f
    };

    private static int indices[] = {
            1, 4, 0,
            4, 9, 0,
            4, 5, 9,
            8, 5, 4,
            1, 8, 4,
            1, 10, 8,
            10, 3, 8,
            8, 3, 5,
            3, 2, 5,
            3, 7, 2,
            3, 10, 7,
            10, 6, 7,
            6, 11, 7,
            6, 0, 11,
            6, 1, 0,
            10, 1, 6,
            11, 0, 9,
            2, 11, 9,
            5, 2, 9,
            11, 2, 7
    };

    @Override
    public PlanetData access(final String file) {
        return new PlanetData(vertices, indices);
    }

}
