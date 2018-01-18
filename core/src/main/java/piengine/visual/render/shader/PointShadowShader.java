package piengine.visual.render.shader;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import piengine.visual.shader.domain.Shader;
import piengine.visual.shader.domain.ShaderDao;
import piengine.visual.shader.domain.uniform.UniformFloat;
import piengine.visual.shader.domain.uniform.UniformMatrix4f;
import piengine.visual.shader.domain.uniform.UniformVector3f;

import static piengine.visual.pointshadow.domain.PointShadow.CAMERA_COUNT;

public class PointShadowShader extends Shader {

    private final UniformMatrix4f modelMatrix = new UniformMatrix4f(this, "modelMatrix");
    private final UniformMatrix4f[] projectionViewMatrices = uniformMatrix4fArray("projectionViewMatrices", CAMERA_COUNT);
    private final UniformVector3f lightPosition = new UniformVector3f(this, "lightPosition");
    private final UniformFloat farPlane = new UniformFloat(this, "farPlane");

    public PointShadowShader(final ShaderDao dao) {
        super(dao);
    }

    public PointShadowShader start() {
        startShader();
        return this;
    }

    public PointShadowShader stop() {
        stopShader();
        return this;
    }

    public PointShadowShader loadModelMatrix(final Matrix4f value) {
        modelMatrix.load(value);
        return this;
    }

    public PointShadowShader loadProjectionViewMatrices(final Matrix4f[] values) {
        for (int i = 0; i < CAMERA_COUNT; i++) {
            projectionViewMatrices[i].load(values[i]);
        }
        return this;
    }

    public PointShadowShader loadLightPosition(final Vector3f value) {
        lightPosition.load(value);
        return this;
    }

    public PointShadowShader loadFarPlane(final float value) {
        farPlane.load(value);
        return this;
    }
}
