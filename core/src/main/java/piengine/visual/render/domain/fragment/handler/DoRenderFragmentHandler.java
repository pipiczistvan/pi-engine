package piengine.visual.render.domain.fragment.handler;

import piengine.common.gui.writing.text.domain.Text;
import piengine.core.base.exception.PIEngineException;
import piengine.object.model.domain.Model;
import piengine.visual.render.domain.RenderContext;
import piengine.visual.render.domain.RenderType;
import piengine.visual.render.domain.fragment.domain.RenderFragmentType;
import piengine.visual.render.service.AbstractRenderService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import java.util.List;

import static piengine.visual.render.domain.fragment.domain.RenderFragmentType.DO_RENDER;

@Component
public class DoRenderFragmentHandler extends FragmentHandler<RenderType> {

    private final List<AbstractRenderService> renderServices;

    @Wire
    public DoRenderFragmentHandler(final List<AbstractRenderService> renderServices) {
        this.renderServices = renderServices;
    }

    @Override
    public void handle(final RenderContext context, final RenderType renderType) {
        renderServices.stream()
                .filter(renderService -> renderService.getType() == renderType)
                .findFirst()
                .orElseThrow(() -> new PIEngineException("Invalid render type %s!", renderType.name()))
                .process(context);

        clearContext(context);
    }

    @Override
    public void validate(final RenderContext context, final RenderType renderType) {
        checkDefault(context);

        switch (renderType) {
            case RENDER_PLANE_MODEL:
                checkPlaneModelRendering(context);
            case RENDER_SOLID_MODEL:
                checkSolidModelRendering(context);
                break;
            case RENDER_TEXT:
                checkTextRendering(context);
                break;
            case RENDER_PLANET:
                checkPlanetRendering(context);
                break;
            default:
                throw new PIEngineException("Invalid render type %s!", renderType.name());
        }

        clearContext(context);
    }

    @Override
    public RenderFragmentType getType() {
        return DO_RENDER;
    }

    private void checkPlaneModelRendering(final RenderContext context) {
        for (Model model : context.models) {
            check("model", notNull(model));
        }
    }

    private void checkSolidModelRendering(final RenderContext context) {
        for (Model model : context.models) {
            check("model", notNull(model));
        }
    }

    private void checkTextRendering(final RenderContext context) {
        for (Text text : context.texts) {
            check("text", notNull(text));
        }
    }

    private void checkDefault(final RenderContext context) {
        check("camera", notNull(context.camera));
    }

    private void checkPlanetRendering(final RenderContext context) {
        check("planet", notNull(context.planet));
    }

    private void clearContext(final RenderContext context) {
        context.viewport.set(0);
        context.clearColor.set(0);
        context.models.clear();
        context.texts.clear();
        context.asset = null;
        context.texture = null;
        context.camera = null;
        context.light = null;
        context.planet = null;
        context.color = null;
    }

}
