package piengine.object.animation.accessor;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import piengine.core.xml.collada.domain.animation.Animation;
import piengine.core.xml.collada.domain.common.Input;
import piengine.core.xml.collada.domain.common.Node;
import piengine.core.xml.collada.domain.common.Source;
import piengine.core.xml.collada.domain.visualscene.VisualScene;
import piengine.object.animation.domain.AnimationData;
import piengine.object.animation.domain.JointTransformData;
import piengine.object.animation.domain.KeyFrameData;
import puppeteer.annotation.premade.Component;

import java.nio.FloatBuffer;

import static piengine.core.xml.collada.domain.common.Input.findInputBySemantic;
import static piengine.core.xml.collada.domain.common.Source.findSourceById;

@Component
public class AnimationDataParser {

    private static final Matrix4f CORRECTION = new Matrix4f().rotate((float) Math.toRadians(-90), new Vector3f(1, 0, 0));

    public AnimationData parse(final Animation[] animations, final VisualScene visualScene) {
        String rootNode = findRootJointName(visualScene);
        float[] times = animations[0].source[0].float_array;
        float duration = times[times.length - 1];
        KeyFrameData[] keyFrames = initKeyFrames(times);
        for (Animation jointNode : animations) {
            loadJointTransforms(keyFrames, jointNode, rootNode);
        }

        return new AnimationData(duration, keyFrames);
    }

    private KeyFrameData[] initKeyFrames(float[] times) {
        KeyFrameData[] frames = new KeyFrameData[times.length];
        for (int i = 0; i < frames.length; i++) {
            frames[i] = new KeyFrameData(times[i]);
        }
        return frames;
    }

    private void loadJointTransforms(final KeyFrameData[] frames, final Animation jointData, final String rootNodeId) {
        String jointNameId = getJointName(jointData);
        String dataId = getDataId(jointData);
        Source source = findSourceById(jointData.source, dataId);
        processTransforms(jointNameId, source.float_array, frames, jointNameId.equals(rootNodeId));
    }

    private String getDataId(final Animation jointData) {
        Input input = findInputBySemantic(jointData.sampler.input, "OUTPUT");
        return input.source.substring(1);
    }

    private String getJointName(final Animation jointData) {
        return jointData.channel.target.split("/")[0];
    }

    private void processTransforms(final String jointName, final float[] rawData, final KeyFrameData[] keyFrames, final boolean root) {FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
        float[] matrixData = new float[16];
        for (int i = 0; i < keyFrames.length; i++) {
            System.arraycopy(rawData, i * 16, matrixData, 0, 16);
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

    private String findRootJointName(final VisualScene visualScene) {
        Node skeleton = Node.findNodeById(visualScene.node, "Armature");
        return skeleton.node[0].id;
    }
}
