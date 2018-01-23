package piengine.object.animatedmodel.accessor;

import piengine.core.base.api.Accessor;
import piengine.core.base.resource.ResourceLoader;
import piengine.core.xml.domain.XmlNode;
import piengine.core.xml.service.XmlParser;
import piengine.object.animatedmodel.domain.AnimatedModelData;
import piengine.object.animatedmodel.domain.AnimatedModelKey;
import piengine.object.animatedmodel.domain.GeometryData;
import piengine.object.animatedmodel.domain.SkeletonData;
import piengine.object.animatedmodel.domain.SkinningData;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import static piengine.core.base.type.property.ApplicationProperties.get;
import static piengine.core.base.type.property.PropertyKeys.COLLADA_LOCATION;

@Component
public class AnimatedModelAccessor implements Accessor<AnimatedModelKey, AnimatedModelData> {

    private static final String ROOT = get(COLLADA_LOCATION);
    private static final String COLLADA_EXT = "dae";

    private final XmlParser xmlParser;
    private final SkinningDataParser skinningDataParser;
    private final SkeletonDataParser skeletonDataParser;
    private final GeometryDataParser geometryDataParser;
    private final ResourceLoader loader;

    @Wire
    public AnimatedModelAccessor(final XmlParser xmlParser, final SkinningDataParser skinningDataParser,
                                 final SkeletonDataParser skeletonDataParser, final GeometryDataParser geometryDataParser) {
        this.xmlParser = xmlParser;
        this.skinningDataParser = skinningDataParser;
        this.skeletonDataParser = skeletonDataParser;
        this.geometryDataParser = geometryDataParser;
        this.loader = new ResourceLoader(ROOT, COLLADA_EXT);
    }

    @Override
    public AnimatedModelData access(final AnimatedModelKey key) {
        String colladaSource = loader.load(key.file);
        XmlNode node = xmlParser.parseSource(colladaSource.split("\n"));

        SkinningData skinningData = skinningDataParser.parseXmlNode(node.getChild("library_controllers"), key.maxWeights);
        SkeletonData skeletonData = skeletonDataParser.parseXmlNode(node.getChild("library_visual_scenes"), skinningData.jointOrder);
        GeometryData geometryData = geometryDataParser.parseXmlNode(node.getChild("library_geometries"), skinningData.verticesSkinData);

        return new AnimatedModelData(skeletonData, geometryData);
    }
}
