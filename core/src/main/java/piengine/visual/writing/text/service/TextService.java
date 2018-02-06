package piengine.visual.writing.text.service;

import piengine.core.architecture.service.SupplierService;
import piengine.io.interpreter.vertexarray.VertexArray;
import piengine.io.loader.text.domain.TextDto;
import piengine.io.loader.text.loader.TextLoader;
import piengine.visual.writing.text.domain.Text;
import piengine.visual.writing.text.domain.TextConfiguration;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import java.util.ArrayList;
import java.util.List;

import static piengine.io.interpreter.vertexarray.VertexAttribute.TEXTURE_COORD;
import static piengine.io.interpreter.vertexarray.VertexAttribute.VERTEX;

@Component
public class TextService extends SupplierService<TextConfiguration, Text> {

    private final TextLoader textLoader;
    private final List<Text> texts;

    @Wire
    public TextService(final TextLoader textLoader) {
        this.textLoader = textLoader;
        this.texts = new ArrayList<>();
    }

    @Override
    public Text supply(final TextConfiguration key) {
        VertexArray vertexArray = createVertexArray(key);
        Text text = new Text(key.getParent(), vertexArray, key.getFont(), key.getColor());
        texts.add(text);

        return text;
    }

    @Override
    public void terminate() {
        texts.forEach(text -> text.vao.clear());
    }

    public void update(final Text text, final TextConfiguration config) {
        text.vao.clear();

        text.vao = createVertexArray(config);
        text.color = config.getColor();
        text.font = config.getFont();
    }

    private VertexArray createVertexArray(final TextConfiguration key) {
        TextDto text = textLoader.load(key);

        return new VertexArray(text.vertices.length / 2)
                .bind()
                .attachVertexBuffer(VERTEX, text.vertices, 2)
                .attachVertexBuffer(TEXTURE_COORD, text.textureCoords, 2)
                .unbind();
    }
}
