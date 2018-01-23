package piengine.object.animatedmodel.domain;

import java.util.List;

public class SkinningData {

    public final List<String> jointOrder;
    public final List<VertexSkinData> verticesSkinData;

    public SkinningData(final List<String> jointOrder, final List<VertexSkinData> verticesSkinData) {
        this.jointOrder = jointOrder;
        this.verticesSkinData = verticesSkinData;
    }
}
