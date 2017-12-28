package piengine.visual.render.service;

import org.joml.Vector2i;
import org.joml.Vector4f;
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

    public void clearScreen(final Vector4f clearColor) {
        renderInterpreter.setClearColor(clearColor);
        renderInterpreter.clearScreen();
    }

    public void setViewport(final Vector2i viewport) {
        renderInterpreter.setViewport(viewport);
    }

}
