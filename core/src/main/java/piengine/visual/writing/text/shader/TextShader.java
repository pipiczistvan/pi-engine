package piengine.visual.writing.text.shader;


import org.joml.Matrix4f;
import piengine.core.base.type.color.Color;
import piengine.visual.shader.domain.Shader;
import piengine.visual.shader.domain.ShaderDao;
import piengine.visual.shader.domain.uniform.UniformColor;
import piengine.visual.shader.domain.uniform.UniformMatrix4f;

public class TextShader extends Shader {

    private final UniformMatrix4f modelMatrix = new UniformMatrix4f(this, "modelMatrix");
    private final UniformColor color = new UniformColor(this, "color");

    public TextShader(final ShaderDao dao) {
        super(dao);
    }

    public TextShader start() {
        startShader();

        return this;
    }

    public TextShader stop() {
        stopShader();

        return this;
    }


    public TextShader loadModelMatrix(final Matrix4f value) {
        modelMatrix.load(value);

        return this;
    }

    public TextShader loadColor(final Color value) {
        color.load(value);

        return this;
    }
}
