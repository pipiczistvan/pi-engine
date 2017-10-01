package piengine.visual.writing.text.shader;


import org.joml.Vector2f;
import org.joml.Vector3f;
import piengine.visual.shader.domain.Shader;
import piengine.visual.shader.domain.ShaderDao;

public class TextShader extends Shader {

    private int location_translation;
    private int location_color;

    public TextShader(final ShaderDao dao) {
        super(dao);
    }

    @Override
    protected void getUniformLocations() {
        location_translation = super.getUniformLocation("translation");
        location_color = super.getUniformLocation("color");
    }

    public TextShader start() {
        startShader();

        return this;
    }

    public TextShader stop() {
        stopShader();

        return this;
    }

    public void loadTranslation(final Vector2f translation) {
        super.loadUniform(location_translation, translation);
    }

    public void loadColor(final Vector3f color) {
        super.loadUniform(location_color, color);
    }

}
