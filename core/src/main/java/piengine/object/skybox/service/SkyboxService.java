package piengine.object.skybox.service;

import piengine.core.architecture.service.SupplierService;
import piengine.core.base.api.Initializable;
import piengine.io.interpreter.vertexarray.VertexArray;
import piengine.object.skybox.domain.Skybox;
import piengine.visual.texture.cubeimage.domain.CubeImage;
import puppeteer.annotation.premade.Component;

import static piengine.io.interpreter.vertexarray.VertexAttribute.VERTEX;

@Component
public class SkyboxService extends SupplierService<CubeImage, Skybox> implements Initializable {

    private static final float[] VERTICES = {
            -1f, 1f, -1f,
            -1f, -1f, -1f,
            1f, -1f, -1f,
            1f, -1f, -1f,
            1f, 1f, -1f,
            -1f, 1f, -1f,

            -1f, -1f, 1f,
            -1f, -1f, -1f,
            -1f, 1f, -1f,
            -1f, 1f, -1f,
            -1f, 1f, 1f,
            -1f, -1f, 1f,

            1f, -1f, -1f,
            1f, -1f, 1f,
            1f, 1f, 1f,
            1f, 1f, 1f,
            1f, 1f, -1f,
            1f, -1f, -1f,

            -1f, -1f, 1f,
            -1f, 1f, 1f,
            1f, 1f, 1f,
            1f, 1f, 1f,
            1f, -1f, 1f,
            -1f, -1f, 1f,

            -1f, 1f, -1f,
            1f, 1f, -1f,
            1f, 1f, 1f,
            1f, 1f, 1f,
            -1f, 1f, 1f,
            -1f, 1f, -1f,

            -1f, -1f, -1f,
            -1f, -1f, 1f,
            1f, -1f, -1f,
            1f, -1f, -1f,
            -1f, -1f, 1f,
            1f, -1f, 1f
    };

    private VertexArray skyboxVertexArray;

    @Override
    public void initialize() {
        skyboxVertexArray = createVertexArray();
    }

    @Override
    public Skybox supply(final CubeImage key) {
        return new Skybox(key, skyboxVertexArray);
    }

    @Override
    public void terminate() {
        skyboxVertexArray.clear();
    }

    private VertexArray createVertexArray() {
        return new VertexArray(VERTICES.length / 3)
                .bind()
                .attachVertexBuffer(VERTEX, VERTICES, 3)
                .unbind();
    }
}
