package piengine.visual.lighting.directional.shadow.shader;

import org.joml.Matrix4f;
import piengine.io.interpreter.shader.Shader;
import piengine.io.interpreter.shader.uniform.UniformInteger;
import piengine.io.interpreter.shader.uniform.UniformMatrix4f;
import piengine.io.loader.glsl.domain.GlslDto;

import static piengine.core.base.type.property.ApplicationProperties.get;
import static piengine.core.base.type.property.PropertyKeys.ANIMATION_SKELETON_MAX_JOINTS;
import static piengine.io.interpreter.shader.uniform.UniformInteger.uniformInteger;
import static piengine.io.interpreter.shader.uniform.UniformMatrix4f.uniformMatrix4f;

public class DirectionalShadowShader extends Shader {

    private static final int MAX_JOINTS = get(ANIMATION_SKELETON_MAX_JOINTS);

    private final UniformInteger renderStage = uniformInteger(this, "renderStage");
    private final UniformMatrix4f transformationMatrix = uniformMatrix4f(this, "transformationMatrix");
    private final UniformMatrix4f[] jointTransforms = uniformMatrix4f(this, "jointTransforms", MAX_JOINTS);

    public DirectionalShadowShader(final GlslDto glsl) {
        super(glsl);
    }

    public DirectionalShadowShader loadRenderStage(final int value) {
        renderStage.load(value);

        return this;
    }

    public DirectionalShadowShader loadTransformationMatrix(final Matrix4f value) {
        transformationMatrix.load(value);

        return this;
    }

    public DirectionalShadowShader loadJointTransforms(final Matrix4f[] value) {
        for (int i = 0; i < value.length; i++) {
            jointTransforms[i].load(value[i]);
        }

        return this;
    }
}
