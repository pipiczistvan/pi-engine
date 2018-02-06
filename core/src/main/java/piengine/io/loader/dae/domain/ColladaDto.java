package piengine.io.loader.dae.domain;

import piengine.io.loader.Dto;

public class ColladaDto implements Dto {

    public final SkeletonData skeletonData;
    public final GeometryData geometryData;
    public final AnimationData animationData;

    public ColladaDto(final SkeletonData skeletonData, final GeometryData geometryData, final AnimationData animationData) {
        this.skeletonData = skeletonData;
        this.geometryData = geometryData;
        this.animationData = animationData;
    }
}
