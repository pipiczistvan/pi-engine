package piengine.visual.writing.text.shader;


import org.joml.Matrix4f;
import org.joml.Vector2f;
import piengine.core.base.type.color.Color;
import piengine.visual.shader.domain.Shader;
import piengine.visual.shader.domain.ShaderDao;
import piengine.visual.shader.domain.uniform.UniformColor;
import piengine.visual.shader.domain.uniform.UniformFloat;
import piengine.visual.shader.domain.uniform.UniformMatrix4f;
import piengine.visual.shader.domain.uniform.UniformVector2f;

import static piengine.visual.shader.domain.uniform.UniformColor.uniformColor;
import static piengine.visual.shader.domain.uniform.UniformFloat.uniformFloat;
import static piengine.visual.shader.domain.uniform.UniformMatrix4f.uniformMatrix4f;
import static piengine.visual.shader.domain.uniform.UniformVector2f.uniformVector2f;

public class TextShader extends Shader {

    private final UniformMatrix4f modelMatrix = uniformMatrix4f(this, "modelMatrix");
    private final UniformColor color = uniformColor(this, "color");
    private final UniformFloat width = uniformFloat(this, "width");
    private final UniformFloat edge = uniformFloat(this, "edge");
    private final UniformFloat borderWidth = uniformFloat(this, "borderWidth");
    private final UniformFloat borderEdge = uniformFloat(this, "borderEdge");
    private final UniformColor outlineColor = uniformColor(this, "outlineColor");
    private final UniformVector2f offset = uniformVector2f(this, "offset");

    public TextShader(final ShaderDao dao) {
        super(dao);
    }

    public TextShader loadModelMatrix(final Matrix4f value) {
        modelMatrix.load(value);

        return this;
    }

    public TextShader loadColor(final Color value) {
        color.load(value);

        return this;
    }

    public TextShader loadWidth(final float value) {
        width.load(value);

        return this;
    }

    public TextShader loadEdge(final float value) {
        edge.load(value);

        return this;
    }

    public TextShader loadBorderWidth(final float value) {
        borderWidth.load(value);

        return this;
    }

    public TextShader loadBorderEdge(final float value) {
        borderEdge.load(value);

        return this;
    }

    public TextShader loadOutlineColor(final Color value) {
        outlineColor.load(value);

        return this;
    }

    public TextShader loadOffset(final Vector2f value) {
        offset.load(value);

        return this;
    }
}
