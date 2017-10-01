package piengine.visual.render.service;

import piengine.visual.render.domain.RenderContext;
import piengine.visual.render.domain.RenderPlan;
import piengine.visual.render.domain.fragment.RenderFragment;
import piengine.visual.render.domain.fragment.handler.FragmentHandler;
import piengine.visual.render.interpreter.RenderInterpreter;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import java.util.List;

@Component
public class RenderService {

    private final RenderInterpreter renderInterpreter;
    private final List<FragmentHandler> fragmentHandlers;

    @Wire
    public RenderService(final RenderInterpreter renderInterpreter,
                         final List<FragmentHandler> fragmentHandlers) {
        this.renderInterpreter = renderInterpreter;
        this.fragmentHandlers = fragmentHandlers;
    }

    public void validate(final RenderPlan plan) {
        if (plan == null) {
            return;
        }
        RenderContext context = new RenderContext();

        for (RenderFragment fragment : plan.fragments) {
            fragmentHandlers.stream()
                    .filter(handler -> handler.getType() == fragment.type)
                    .findFirst()
                    .ifPresent(handler -> handler.validate(context, fragment.value));
        }
    }

    public void render(final RenderPlan plan) {
        if (plan == null) {
            return;
        }
        RenderContext context = new RenderContext();

        for (RenderFragment fragment : plan.fragments) {
            fragmentHandlers.stream()
                    .filter(handler -> handler.getType() == fragment.type)
                    .findFirst()
                    .ifPresent(handler -> handler.handle(context, fragment.value));
        }
    }

    public void setWireFrameMode(final boolean wireFrameMode) {
        renderInterpreter.setWireFrameMode(wireFrameMode);
    }

}
