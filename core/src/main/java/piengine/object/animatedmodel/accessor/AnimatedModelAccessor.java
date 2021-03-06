package piengine.object.animatedmodel.accessor;

import piengine.core.base.api.Accessor;
import piengine.core.xml.collada.domain.Collada;
import piengine.core.xml.collada.service.ColladaParser;
import piengine.object.animatedmodel.domain.AnimatedModelData;
import piengine.object.animatedmodel.domain.AnimatedModelKey;
import piengine.object.animatedmodel.domain.GeometryData;
import piengine.object.animatedmodel.domain.SkeletonData;
import piengine.object.animatedmodel.domain.SkinningData;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

@Component
public class AnimatedModelAccessor extends Accessor<AnimatedModelKey, AnimatedModelData> {

    private final ColladaParser colladaParser;
    private final SkinningDataParser skinningDataParser;
    private final SkeletonDataParser skeletonDataParser;
    private final GeometryDataParser geometryDataParser;

    @Wire
    public AnimatedModelAccessor(final ColladaParser colladaParser) {
        this.colladaParser = colladaParser;
        this.skinningDataParser = new SkinningDataParser();
        this.skeletonDataParser = new SkeletonDataParser();
        this.geometryDataParser = new GeometryDataParser();
    }

    @Override
    protected AnimatedModelData accessResource(final AnimatedModelKey key) {
        Collada collada = colladaParser.parse(key.colladaFile);
        SkinningData skinningData = skinningDataParser.parse(collada.library_controllers[0], key.maxWeights);
        SkeletonData skeletonData = skeletonDataParser.parse(collada.library_visual_scenes[0], skinningData.jointOrder);
        GeometryData geometryData = geometryDataParser.parse(collada.library_geometries[0], skinningData.verticesSkinData);

        return new AnimatedModelData(key.parent, key.texture, skeletonData, geometryData);
    }

    @Override
    protected String getAccessInfo(final AnimatedModelKey key, final AnimatedModelData resource) {
        return String.format("File: %s", key.colladaFile);
    }
}
