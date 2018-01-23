package piengine.object.animatedmodel.accessor;

import piengine.core.xml.domain.XmlNode;
import piengine.object.animatedmodel.domain.SkinningData;
import piengine.object.animatedmodel.domain.VertexSkinData;
import puppeteer.annotation.premade.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SkinningDataParser {

    public SkinningData parseXmlNode(final XmlNode controllersNode, final int maxWeights) {
        List<String> jointsList = loadJointsList(controllersNode);
        float[] weights = loadWeights(controllersNode);
        XmlNode weightsDataNode = controllersNode.getChild("vertex_weights");
        int[] effectorJointCounts = getEffectiveJointsCounts(weightsDataNode);
        List<VertexSkinData> vertexWeights = getSkinData(weightsDataNode, effectorJointCounts, weights, maxWeights);
        return new SkinningData(jointsList, vertexWeights);
    }

    private List<String> loadJointsList(final XmlNode controllersNode) {
        XmlNode inputNode = controllersNode.getChild("vertex_weights");
        String jointDataId = inputNode.getChildWithAttribute("input", "semantic", "JOINT").getAttribute("source")
                .substring(1);
        XmlNode jointsNode = controllersNode.getChildWithAttribute("source", "id", jointDataId).getChild("Name_array");
        String[] names = jointsNode.getData().split(" ");
        List<String> jointsList = new ArrayList<String>();
        for (String name : names) {
            jointsList.add(name);
        }
        return jointsList;
    }

    private float[] loadWeights(final XmlNode controllersNode) {
        XmlNode inputNode = controllersNode.getChild("vertex_weights");
        String weightsDataId = inputNode.getChildWithAttribute("input", "semantic", "WEIGHT").getAttribute("source")
                .substring(1);
        XmlNode weightsNode = controllersNode.getChildWithAttribute("source", "id", weightsDataId).getChild("float_array");
        String[] rawData = weightsNode.getData().split(" ");
        float[] weights = new float[rawData.length];
        for (int i = 0; i < weights.length; i++) {
            weights[i] = Float.parseFloat(rawData[i]);
        }
        return weights;
    }

    private int[] getEffectiveJointsCounts(final XmlNode weightsDataNode) {
        String[] rawData = weightsDataNode.getChild("vcount").getData().split(" ");
        int[] counts = new int[rawData.length];
        for (int i = 0; i < rawData.length; i++) {
            counts[i] = Integer.parseInt(rawData[i]);
        }
        return counts;
    }

    private List<VertexSkinData> getSkinData(final XmlNode weightsDataNode, final int[] counts, final float[] weights, final int maxWeights) {
        String[] rawData = weightsDataNode.getChild("v").getData().split(" ");
        List<VertexSkinData> skinningData = new ArrayList<>();
        int pointer = 0;
        for (int count : counts) {
            VertexSkinData skinData = new VertexSkinData();
            for (int i = 0; i < count; i++) {
                int jointId = Integer.parseInt(rawData[pointer++]);
                int weightId = Integer.parseInt(rawData[pointer++]);
                skinData.addJointEffect(jointId, weights[weightId]);
            }
            skinData.limitJointNumber(maxWeights);
            skinningData.add(skinData);
        }
        return skinningData;
    }
}
