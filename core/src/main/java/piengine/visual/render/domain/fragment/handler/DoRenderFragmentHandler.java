package piengine.visual.render.domain.fragment.handler;

import piengine.core.base.exception.PIEngineException;
import piengine.object.model.domain.Model;
import piengine.visual.render.domain.RenderContext;
import piengine.visual.render.domain.RenderType;
import piengine.visual.render.domain.fragment.domain.RenderFragmentType;
import piengine.visual.render.service.AbstractRenderService;
import piengine.visual.writing.text.domain.Text;
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
        switch (renderType) {
            case RENDER_PLANE_MODEL:
                check2DRendering(context);
            case RENDER_SOLID_MODEL:
                check3DRendering(context);
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

    private void check2DRendering(final RenderContext context) {
        for (Model model : context.models) {
            check("model", notNull(model));
        }
        check("texture", notNull(context.texture));
    }

    private void check3DRendering(final RenderContext context) {
        for (Model model : context.models) {
            check("model", notNull(model));
        }
        check("texture", notNull(context.texture));
        check("camera", notNull(context.camera));
        check("light", notNull(context.light));
    }

    private void checkTextRendering(final RenderContext context) {
        for (Text text : context.texts) {
            check("text", notNull(text));
        }
    }

    private void checkPlanetRendering(final RenderContext context) {
        check("planet", notNull(context.planet));
        check("camera", notNull(context.camera));
        check("light", notNull(context.light));
    }

    private void clearContext(final RenderContext context) {
        context.models.clear();
        context.texts.clear();
        context.asset = null;
    }

}
