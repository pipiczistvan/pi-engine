package piengine.visual.render.domain.fragment.handler;

import piengine.common.gui.writing.text.domain.Text;
import piengine.visual.render.domain.RenderContext;
import piengine.visual.render.domain.fragment.domain.RenderFragmentType;
import puppeteer.annotation.premade.Component;

import java.util.List;

import static piengine.visual.render.domain.fragment.domain.RenderFragmentType.SET_TEXT;

@Component
public class SetTextsFragmentHandler extends FragmentHandler<List<Text>> {

    @Override
    public void handle(final RenderContext context, final List<Text> texts) {
        context.texts.addAll(texts);
    }

    @Override
    public RenderFragmentType getType() {
        return SET_TEXT;
    }

}
