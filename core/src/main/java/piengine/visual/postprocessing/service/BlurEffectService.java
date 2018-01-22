package piengine.visual.postprocessing.service;

import org.joml.Vector2i;
import piengine.visual.postprocessing.domain.EffectType;
import piengine.visual.postprocessing.domain.context.BlurEffectContext;
import piengine.visual.postprocessing.domain.context.HorizontalBlurEffectContext;
import piengine.visual.postprocessing.domain.context.VerticalBlurEffectContext;
import piengine.visual.texture.domain.Texture;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import static piengine.visual.postprocessing.domain.EffectType.BLUR_EFFECT;

@Component
public class BlurEffectService extends AbstractPostProcessingService<BlurEffectContext> {

    private final HorizontalBlurEffectService horizontalBlurEffectService;
    private final VerticalBlurEffectService verticalBlurEffectService;

    @Wire
    public BlurEffectService(final HorizontalBlurEffectService horizontalBlurEffectService,
                             final VerticalBlurEffectService verticalBlurEffectService) {
        this.horizontalBlurEffectService = horizontalBlurEffectService;
        this.verticalBlurEffectService = verticalBlurEffectService;
    }

    @Override
    public BlurEffectContext createContext(final Vector2i outSize) {
        HorizontalBlurEffectContext horizontalBlurEffectContext = horizontalBlurEffectService.createContext(outSize);
        VerticalBlurEffectContext verticalBlurEffectContext = verticalBlurEffectService.createContext(outSize);

        return new BlurEffectContext(horizontalBlurEffectContext, verticalBlurEffectContext);
    }

    @Override
    public Texture process(final Texture inTexture, final BlurEffectContext context) {
        Texture horizontalBlur = horizontalBlurEffectService.process(inTexture, context.horizontalBlurEffectContext);
        Texture verticalBlur = verticalBlurEffectService.process(horizontalBlur, context.verticalBlurEffectContext);

        return verticalBlur;
    }

    @Override
    public EffectType getEffectType() {
        return BLUR_EFFECT;
    }
}
