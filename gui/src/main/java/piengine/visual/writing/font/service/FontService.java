package piengine.visual.writing.font.service;

import piengine.common.gui.writing.font.domain.Font;
import piengine.common.gui.writing.font.domain.FontDao;
import piengine.common.gui.writing.font.domain.FontData;
import piengine.core.base.resource.SupplierService;
import piengine.visual.writing.font.accessor.FontAccessor;
import piengine.visual.writing.font.interpreter.FontInterpreter;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

@Component
public class FontService extends SupplierService<Font, FontDao, FontData> {

    private final FontInterpreter fontInterpreter;

    @Wire
    public FontService(final FontAccessor accessor, final FontInterpreter interpreter) {
        super(accessor, interpreter);
        this.fontInterpreter = interpreter;
    }

    @Override
    protected Font createDomain(FontDao dao, FontData resource) {
        return new Font(dao, resource);
    }

    public void bind(final Font font) {
        fontInterpreter.bind(font.dao);
    }

}
