package piengine.object.animation.accessor;

import piengine.core.base.api.Accessor;
import piengine.core.base.resource.ResourceLoader;
import piengine.core.xml.domain.XmlNode;
import piengine.core.xml.service.XmlParser;
import piengine.object.animation.domain.AnimationData;
import piengine.object.animation.domain.AnimationKey;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import static piengine.core.base.type.property.ApplicationProperties.get;
import static piengine.core.base.type.property.PropertyKeys.COLLADA_LOCATION;

@Component
public class AnimationAccessor implements Accessor<AnimationKey, AnimationData> {

    private static final String ROOT = get(COLLADA_LOCATION);
    private static final String COLLADA_EXT = "dae";

    private final XmlParser xmlParser;
    private final AnimationDataParser animationDataParser;
    private final ResourceLoader loader;

    @Wire
    public AnimationAccessor(final XmlParser xmlParser, final AnimationDataParser animationDataParser) {
        this.xmlParser = xmlParser;
        this.animationDataParser = animationDataParser;
        this.loader = new ResourceLoader(ROOT, COLLADA_EXT);
    }

    @Override
    public AnimationData access(final AnimationKey key) {
        String colladaSource = loader.load(key.file);
        XmlNode node = xmlParser.parseSource(colladaSource.split("\n"));

        return animationDataParser.parseXmlNode(node);
    }
}
