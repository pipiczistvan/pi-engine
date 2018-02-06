package piengine.object.skybox.domain;

import org.joml.Matrix4f;
import piengine.core.base.domain.Domain;
import piengine.core.utils.VectorUtils;
import piengine.io.interpreter.vertexarray.VertexArray;
import piengine.object.camera.domain.Camera;
import piengine.visual.texture.cubeimage.domain.CubeImage;

public class Skybox implements Domain {

    private final CubeImage cubeImage;
    private final VertexArray vao;
    private float rotation = 0;

    public Skybox(final CubeImage cubeImage, final VertexArray vao) {
        this.cubeImage = cubeImage;
        this.vao = vao;
    }

    public CubeImage getCubeImage() {
        return cubeImage;
    }

    public VertexArray getVao() {
        return vao;
    }

    public Matrix4f getView(final Camera camera) {
        Matrix4f viewMatrix = new Matrix4f(camera.getView());

        viewMatrix.m30(0);
        viewMatrix.m31(0);
        viewMatrix.m32(0);
        viewMatrix.rotate((float) Math.toRadians(rotation), VectorUtils.UP);

        return viewMatrix;
    }

    public void addRotation(final float rotation) {
        this.rotation += rotation;
    }
}
