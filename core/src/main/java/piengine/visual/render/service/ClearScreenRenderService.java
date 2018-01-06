package piengine.visual.render.service;

import piengine.core.base.type.color.Color;
import piengine.visual.render.interpreter.RenderInterpreter;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

@Component
public class ClearScreenRenderService {

    private final RenderInterpreter renderInterpreter;

    @Wire
    public ClearScreenRenderService(final RenderInterpreter renderInterpreter) {
        this.renderInterpreter = renderInterpreter;
    }

    public void clearScreen(final Color clearColor) {
        renderInterpreter.setClearColor(clearColor);
        renderInterpreter.clearScreen();
    }

}
