package piengine.visual.writing.text.service;

import piengine.object.entity.domain.Entity;
import piengine.visual.writing.text.accessor.TextAccessor;
import piengine.visual.writing.text.domain.Text;
import piengine.visual.writing.text.domain.TextConfiguration;
import piengine.visual.writing.text.domain.TextDao;
import piengine.visual.writing.text.domain.TextData;
import piengine.visual.writing.text.interpreter.TextInterpreter;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

@Component
public class TextService {

    private final TextAccessor textAccessor;
    private final TextInterpreter textInterpreter;

    @Wire
    public TextService(final TextAccessor accessor, final TextInterpreter interpreter) {
        this.textAccessor = accessor;
        this.textInterpreter = interpreter;
    }

    public Text supply(final TextConfiguration config, final Entity parent) {
        TextData data = textAccessor.access(config);
        TextDao dao = textInterpreter.create(data);

        return new Text(config.getFont(), config.getColor(), parent, dao);
    }

    public void update(final Text text, final TextConfiguration config) {
        TextData data = textAccessor.access(config);
        textInterpreter.update(text.getDao(), data);
    }
}
