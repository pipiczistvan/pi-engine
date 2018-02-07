package piengine.object.animatedmodel.accessor;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import piengine.core.xml.collada.domain.common.Node;
import piengine.core.xml.collada.domain.visualscene.VisualScene;
import piengine.object.animatedmodel.domain.JointData;
import piengine.object.animatedmodel.domain.SkeletonData;

import java.util.List;

import static piengine.core.xml.collada.domain.common.Node.findNodeById;

public class SkeletonDataParser {

    private static final Matrix4f CORRECTION = new Matrix4f().rotate((float) Math.toRadians(-90), new Vector3f(1, 0, 0));

    public SkeletonData parse(final VisualScene visualScene, final List<String> boneOrder) {
        Node headNode = findNodeById(visualScene.node, "Armature").node[0];

        JointCountHolder countHolder = new JointCountHolder();
        JointData headJoint = loadJointData(headNode, true, boneOrder, countHolder);
        return new SkeletonData(countHolder.value(), headJoint);
    }

    private JointData loadJointData(final Node jointNode, final boolean isRoot, final List<String> boneOrder, final JointCountHolder countHolder) {
        JointData joint = extractMainJointData(jointNode, isRoot, boneOrder, countHolder);
        for (Node childNode : jointNode.node) {
            joint.addChild(loadJointData(childNode, false, boneOrder, countHolder));
        }
        return joint;
    }

    private JointData extractMainJointData(final Node jointNode, final boolean isRoot, final List<String> boneOrder, final JointCountHolder countHolder) {
        String nameId = jointNode.id;
        int index = boneOrder.indexOf(nameId);
        Matrix4f matrix = new Matrix4f();
        matrix.set(jointNode.matrix);
        matrix.transpose();
        if (isRoot) {
            //because in Blender z is up, but in our game y is up.
            CORRECTION.mul(matrix, matrix);
        }
        countHolder.increase();
        return new JointData(index, nameId, matrix);
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
