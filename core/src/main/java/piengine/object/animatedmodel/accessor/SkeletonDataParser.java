package piengine.object.animatedmodel.accessor;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import piengine.core.xml.domain.XmlNode;
import piengine.object.animatedmodel.domain.JointData;
import piengine.object.animatedmodel.domain.SkeletonData;
import puppeteer.annotation.premade.Component;

import java.nio.FloatBuffer;
import java.util.List;

@Component
public class SkeletonDataParser {

    private static final Matrix4f CORRECTION = new Matrix4f().rotate((float) Math.toRadians(-90), new Vector3f(1, 0, 0));

    public SkeletonData parseXmlNode(final XmlNode visualSceneNode, final List<String> boneOrder) {
        XmlNode headNode = visualSceneNode.getChild("visual_scene").getChildWithAttribute("node", "id", "Armature").getChild("node");
        JointCountHolder countHolder = new JointCountHolder();
        JointData headJoint = loadJointData(headNode, true, boneOrder, countHolder);
        return new SkeletonData(countHolder.value(), headJoint);
    }

    private JointData loadJointData(final XmlNode jointNode, final boolean isRoot, final List<String> boneOrder, final JointCountHolder countHolder) {
        JointData joint = extractMainJointData(jointNode, isRoot, boneOrder, countHolder);
        for (XmlNode childNode : jointNode.getChildren("node")) {
            joint.addChild(loadJointData(childNode, false, boneOrder, countHolder));
        }
        return joint;
    }

    private JointData extractMainJointData(final XmlNode jointNode, final boolean isRoot, final List<String> boneOrder, final JointCountHolder countHolder) {
        String nameId = jointNode.getAttribute("id");
        int index = boneOrder.indexOf(nameId);
        String[] matrixData = jointNode.getChild("matrix").getData().split(" ");
        Matrix4f matrix = new Matrix4f();
        matrix.set(convertData(matrixData));
        matrix.transpose();
        if (isRoot) {
            //because in Blender z is up, but in our game y is up.
            CORRECTION.mul(matrix, matrix);
        }
        countHolder.increase();
        return new JointData(index, nameId, matrix);
    }

    private FloatBuffer convertData(final String[] rawData) {
        float[] matrixData = new float[16];
        for (int i = 0; i < matrixData.length; i++) {
            matrixData[i] = Float.parseFloat(rawData[i]);
        }
        FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
        buffer.put(matrixData);
        buffer.flip();
        return buffer;
    }

    private class JointCountHolder {

        private int jointCount = 0;

        private void increase() {
            jointCount++;
        }

        private int value() {
            return jointCount;
        }
    }
}
