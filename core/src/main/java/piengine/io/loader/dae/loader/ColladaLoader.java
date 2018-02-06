package piengine.io.loader.dae.loader;

import piengine.core.base.exception.PIEngineException;
import piengine.core.xml.collada.Collada;
import piengine.io.loader.AbstractLoader;
import piengine.io.loader.ResourceLoader;
import piengine.io.loader.dae.domain.AnimationData;
import piengine.io.loader.dae.domain.ColladaDto;
import piengine.io.loader.dae.domain.GeometryData;
import piengine.io.loader.dae.domain.SkeletonData;
import piengine.io.loader.dae.domain.SkinningData;
import piengine.io.loader.dae.parser.AnimationDataParser;
import piengine.io.loader.dae.parser.GeometryDataParser;
import piengine.io.loader.dae.parser.SkeletonDataParser;
import piengine.io.loader.dae.parser.SkinningDataParser;
import puppeteer.annotation.premade.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import static piengine.core.base.type.property.ApplicationProperties.get;
import static piengine.core.base.type.property.PropertyKeys.ANIMATION_SKELETON_MAX_WEIGHTS;
import static piengine.core.base.type.property.PropertyKeys.COLLADA_LOCATION;

@Component
public class ColladaLoader extends AbstractLoader<String, ColladaDto> {

    private static final int MAX_WEIGHTS = get(ANIMATION_SKELETON_MAX_WEIGHTS);
    private static final String ROOT = get(COLLADA_LOCATION);
    private static final String COLLADA_EXT = "dae";

    private final ResourceLoader loader;
    private final SkinningDataParser skinningDataParser;
    private final SkeletonDataParser skeletonDataParser;
    private final GeometryDataParser geometryDataParser;
    private final AnimationDataParser animationDataParser;
    private final Unmarshaller unmarshaller;

    public ColladaLoader() throws JAXBException {
        this.loader = new ResourceLoader(ROOT, COLLADA_EXT);
        this.skinningDataParser = new SkinningDataParser();
        this.skeletonDataParser = new SkeletonDataParser();
        this.geometryDataParser = new GeometryDataParser();
        this.animationDataParser = new AnimationDataParser();
        this.unmarshaller = JAXBContext.newInstance(Collada.class).createUnmarshaller();
    }

    @Override
    protected ColladaDto createDto(final String key) {
        Collada collada = parseXml(key);
        SkinningData skinningData = skinningDataParser.parse(collada.library_controllers[0], MAX_WEIGHTS);
        SkeletonData skeletonData = skeletonDataParser.parse(collada.library_visual_scenes[0], skinningData.jointOrder);
        GeometryData geometryData = geometryDataParser.parse(collada.library_geometries[0], skinningData.verticesSkinData);
        AnimationData animationData = animationDataParser.parse(collada.library_animations, collada.library_visual_scenes[0]);

        return new ColladaDto(skeletonData, geometryData, animationData);
    }

    private Collada parseXml(final String file) {
        try {
            return (Collada) unmarshaller.unmarshal(loader.getUrl(file));
        } catch (JAXBException e) {
            throw new PIEngineException("Could not unmarshal collada file: %s!", file);
        }
    }
}
