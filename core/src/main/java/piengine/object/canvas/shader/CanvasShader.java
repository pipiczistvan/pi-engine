package piengine.object.canvas.shader;

import org.joml.Matrix4f;
import piengine.core.base.type.color.Color;
import piengine.io.interpreter.shader.Shader;
import piengine.io.interpreter.shader.uniform.UniformColor;
import piengine.io.interpreter.shader.uniform.UniformMatrix4f;
import piengine.io.loader.glsl.domain.GlslDto;

import static piengine.io.interpreter.shader.uniform.UniformColor.uniformColor;
import static piengine.io.interpreter.shader.uniform.UniformMatrix4f.uniformMatrix4f;

public class CanvasShader extends Shader {

    private final UniformMatrix4f modelMatrix = uniformMatrix4f(this, "modelMatrix");
    private final UniformColor color = uniformColor(this, "color");

    public CanvasShader(final GlslDto glsl) {
        super(glsl);
    }

    public CanvasShader loadModelMatrix(final Matrix4f value) {
        modelMatrix.load(value);

        return this;
    }

    public CanvasShader loadColor(final Color value) {
        color.load(value);

        return this;
    }
}
