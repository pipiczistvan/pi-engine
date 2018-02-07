package piengine.object.animation.accessor;

import piengine.core.base.api.Accessor;
import piengine.core.xml.collada.domain.Collada;
import piengine.core.xml.collada.service.ColladaParser;
import piengine.object.animation.domain.AnimationData;
import piengine.object.animation.domain.AnimationKey;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

@Component
public class AnimationAccessor extends Accessor<AnimationKey, AnimationData> {

    private final ColladaParser colladaParser;
    private final AnimationDataParser animationDataParser;

    @Wire
    public AnimationAccessor(final ColladaParser colladaParser, final AnimationDataParser animationDataParser) {
        this.colladaParser = colladaParser;
        this.animationDataParser = animationDataParser;
    }

    @Override
    protected AnimationData accessResource(final AnimationKey key) {
        Collada collada = colladaParser.parse(key.file);

        return animationDataParser.parse(collada.library_animations, collada.library_visual_scenes[0]);
    }

    @Override
    protected String getAccessInfo(final AnimationKey key, final AnimationData resource) {
        return String.format("File: %s", key.file);
    }
}
