package piengine.io.loader.dae.parser;

import piengine.core.xml.collada.common.Input;
import piengine.core.xml.collada.common.Source;
import piengine.core.xml.collada.controller.Controller;
import piengine.core.xml.collada.controller.Skin;
import piengine.io.loader.dae.domain.SkinningData;
import piengine.io.loader.dae.domain.VertexSkinData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static piengine.core.xml.collada.common.Input.findInputBySemantic;
import static piengine.core.xml.collada.common.Source.findSourceById;

public class SkinningDataParser {

    public SkinningData parse(final Controller controller, final int maxWeights) {
        List<String> jointsList = loadJointsList(controller.skin);
        float[] weights = loadWeights(controller.skin);
        int[] effectorJointCounts = getEffectiveJointsCounts(controller.skin);
        List<VertexSkinData> vertexWeights = getSkinData(controller.skin, effectorJointCounts, weights, maxWeights);
        return new SkinningData(jointsList, vertexWeights);
    }

    private List<String> loadJointsList(final Skin skin) {
        Input input = findInputBySemantic(skin.vertex_weights.input, "JOINT");
        Source source = findSourceById(skin.source, input.source.substring(1));

        return new ArrayList<>(Arrays.asList(source.Name_array));
    }

    private float[] loadWeights(final Skin skin) {
        Input input = findInputBySemantic(skin.vertex_weights.input, "WEIGHT");
        Source source = findSourceById(skin.source, input.source.substring(1));

        float[] weights = new float[source.float_array.length];
        System.arraycopy(source.float_array, 0, weights, 0, weights.length);
        return weights;
    }

    private int[] getEffectiveJointsCounts(final Skin skin) {
        int[] counts = new int[skin.vertex_weights.vcount.length];
        System.arraycopy(skin.vertex_weights.vcount, 0, counts, 0, skin.vertex_weights.vcount.length);
        return counts;
    }

    private List<VertexSkinData> getSkinData(final Skin skin, final int[] counts, final float[] weights, final int maxWeights) {
        List<VertexSkinData> skinningData = new ArrayList<>();
        int pointer = 0;
        for (int count : counts) {
            VertexSkinData skinData = new VertexSkinData();
            for (int i = 0; i < count; i++) {
                int jointId = skin.vertex_weights.v[pointer++];
                int weightId = skin.vertex_weights.v[pointer++];
                skinData.addJointEffect(jointId, weights[weightId]);
            }
            skinData.limitJointNumber(maxWeights);
            skinningData.add(skinData);
        }
        return skinningData;
    }
}
