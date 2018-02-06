package piengine.visual.lighting.point.shadow.shader;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import piengine.io.interpreter.shader.Shader;
import piengine.io.interpreter.shader.uniform.UniformInteger;
import piengine.io.interpreter.shader.uniform.UniformMatrix4f;
import piengine.io.interpreter.shader.uniform.UniformVector3f;
import piengine.io.loader.glsl.domain.GlslDto;

import static piengine.core.base.type.property.ApplicationProperties.get;
import static piengine.core.base.type.property.PropertyKeys.ANIMATION_SKELETON_MAX_JOINTS;
import static piengine.io.interpreter.shader.uniform.UniformInteger.uniformInteger;
import static piengine.io.interpreter.shader.uniform.UniformMatrix4f.uniformMatrix4f;
import static piengine.io.interpreter.shader.uniform.UniformVector3f.uniformVector3f;
import static piengine.visual.lighting.point.shadow.domain.PointShadow.CAMERA_COUNT;

public class PointShadowShader extends Shader {

    private static final int MAX_JOINTS = get(ANIMATION_SKELETON_MAX_JOINTS);

    private final UniformMatrix4f modelMatrix = uniformMatrix4f(this, "modelMatrix");
    private final UniformMatrix4f[] projectionViewMatrices = uniformMatrix4f(this, "projectionViewMatrices", CAMERA_COUNT);
    private final UniformVector3f lightPosition = uniformVector3f(this, "lightPosition");
    private final UniformInteger renderStage = uniformInteger(this, "renderStage");
    private final UniformMatrix4f[] jointTransforms = uniformMatrix4f(this, "jointTransforms", MAX_JOINTS);

    public PointShadowShader(final GlslDto glsl) {
        super(glsl);
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

    public PointShadowShader loadRenderStage(final int value) {
        renderStage.load(value);

        return this;
    }

    public PointShadowShader loadJointTransforms(final Matrix4f[] value) {
        for (int i = 0; i < value.length; i++) {
            jointTransforms[i].load(value[i]);
        }

        return this;
    }
}
