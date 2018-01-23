package piengine.object.animation.accessor;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import piengine.core.xml.domain.XmlNode;
import piengine.object.animation.domain.AnimationData;
import piengine.object.animation.domain.JointTransformData;
import piengine.object.animation.domain.KeyFrameData;
import puppeteer.annotation.premade.Component;

import java.nio.FloatBuffer;
import java.util.List;

@Component
public class AnimationDataParser {

    private static final Matrix4f CORRECTION = new Matrix4f().rotate((float) Math.toRadians(-90), new Vector3f(1, 0, 0));

    public AnimationData parseXmlNode(final XmlNode node) {
        XmlNode animationNode = node.getChild("library_animations");
        XmlNode jointsNode = node.getChild("library_visual_scenes");

        String rootNode = findRootJointName(jointsNode);
        float[] times = getKeyTimes(animationNode);
        float duration = times[times.length - 1];
        KeyFrameData[] keyFrames = initKeyFrames(times);
        List<XmlNode> animationNodes = animationNode.getChildren("animation");
        for (XmlNode jointNode : animationNodes) {
            loadJointTransforms(keyFrames, jointNode, rootNode);
        }

        return new AnimationData(duration, keyFrames);
    }

    private float[] getKeyTimes(final XmlNode animationNode) {
        XmlNode timeData = animationNode.getChild("animation").getChild("source").getChild("float_array");
        String[] rawTimes = timeData.getData().split(" ");
        float[] times = new float[rawTimes.length];
        for (int i = 0; i < times.length; i++) {
            times[i] = Float.parseFloat(rawTimes[i]);
        }
        return times;
    }

    private KeyFrameData[] initKeyFrames(float[] times) {
        KeyFrameData[] frames = new KeyFrameData[times.length];
        for (int i = 0; i < frames.length; i++) {
            frames[i] = new KeyFrameData(times[i]);
        }
        return frames;
    }

    private void loadJointTransforms(KeyFrameData[] frames, XmlNode jointData, String rootNodeId) {
        String jointNameId = getJointName(jointData);
        String dataId = getDataId(jointData);
        XmlNode transformData = jointData.getChildWithAttribute("source", "id", dataId);
        String[] rawData = transformData.getChild("float_array").getData().split(" ");
        processTransforms(jointNameId, rawData, frames, jointNameId.equals(rootNodeId));
    }

    private String getDataId(XmlNode jointData) {
        XmlNode node = jointData.getChild("sampler").getChildWithAttribute("input", "semantic", "OUTPUT");
        return node.getAttribute("source").substring(1);
    }

    private String getJointName(XmlNode jointData) {
        XmlNode channelNode = jointData.getChild("channel");
        String data = channelNode.getAttribute("target");
        return data.split("/")[0];
    }

    private void processTransforms(String jointName, String[] rawData, KeyFrameData[] keyFrames, boolean root) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
        float[] matrixData = new float[16];
        for (int i = 0; i < keyFrames.length; i++) {
            for (int j = 0; j < 16; j++) {
                matrixData[j] = Float.parseFloat(rawData[i * 16 + j]);
            }
            buffer.clear();
            buffer.put(matrixData);
            buffer.flip();
            Matrix4f transform = new Matrix4f();
            transform.set(buffer);
            transform.transpose();
            if (root) {
                //because up axis in Blender is different to up axis in game
                CORRECTION.mul(transform, transform);
            }
            keyFrames[i].addJointTransform(new JointTransformData(jointName, transform));
        }
    }

    private String findRootJointName(final XmlNode jointsNode) {
        XmlNode skeleton = jointsNode.getChild("visual_scene").getChildWithAttribute("node", "id", "Armature");
        return skeleton.getChild("node").getAttribute("id");
    }
}
