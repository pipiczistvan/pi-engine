package piengine.object.animator.domain;

import org.joml.Matrix4f;
import piengine.object.animatedmodel.domain.AnimatedModel;
import piengine.object.animatedmodel.domain.Joint;
import piengine.object.animation.domain.Animation;
import piengine.object.animation.domain.JointTransform;
import piengine.object.animation.domain.KeyFrame;

import java.util.HashMap;
import java.util.Map;

public class Animator {

    private final AnimatedModel entity;

    private Animation currentAnimation;
    private float animationTime = 0;

    public Animator(final AnimatedModel entity) {
        this.entity = entity;
    }

    public void doAnimation(final Animation animation) {
        this.animationTime = 0;
        this.currentAnimation = animation;
    }

    public void update(final float delta) {
        if (currentAnimation == null) {
            return;
        }
        increaseAnimationTime(delta);
        Map<String, Matrix4f> currentPose = calculateCurrentAnimationPose();
        applyPoseToJoints(currentPose, entity.rootJoint, new Matrix4f());
    }

    private void increaseAnimationTime(final float delta) {
        animationTime += delta;
        if (animationTime > currentAnimation.getLength()) {
            this.animationTime %= currentAnimation.getLength();
        }
    }

    private Map<String, Matrix4f> calculateCurrentAnimationPose() {
        KeyFrame[] frames = getPreviousAndNextFrames();
        float progression = calculateProgression(frames[0], frames[1]);
        return interpolatePoses(frames[0], frames[1], progression);
    }

    private void applyPoseToJoints(final Map<String, Matrix4f> currentPose, final Joint joint, final Matrix4f parentTransform) {
        Matrix4f currentLocalTransform = currentPose.get(joint.name);
        Matrix4f currentTransform = new Matrix4f(parentTransform).mul(currentLocalTransform);
        for (Joint childJoint : joint.children) {
            applyPoseToJoints(currentPose, childJoint, currentTransform);
        }
        currentTransform.mul(joint.getInverseBindTransform(), currentTransform);
        joint.setAnimationTransform(currentTransform);
    }

    private KeyFrame[] getPreviousAndNextFrames() {
        KeyFrame[] allFrames = currentAnimation.getKeyFrames();
        KeyFrame previousFrame = allFrames[0];
        KeyFrame nextFrame = allFrames[0];
        for (int i = 1; i < allFrames.length; i++) {
            nextFrame = allFrames[i];
            if (nextFrame.getTimeStamp() > animationTime) {
                break;
            }
            previousFrame = allFrames[i];
        }
        return new KeyFrame[]{previousFrame, nextFrame};
    }

    private float calculateProgression(final KeyFrame previousFrame, final KeyFrame nextFrame) {
        float totalTime = nextFrame.getTimeStamp() - previousFrame.getTimeStamp();
        float currentTime = animationTime - previousFrame.getTimeStamp();
        return currentTime / totalTime;
    }

    private Map<String, Matrix4f> interpolatePoses(final KeyFrame previousFrame, final KeyFrame nextFrame, final float progression) {
        Map<String, Matrix4f> currentPose = new HashMap<>();
        for (String jointName : previousFrame.getJointKeyFrames().keySet()) {
            JointTransform previousTransform = previousFrame.getJointKeyFrames().get(jointName);
            JointTransform nextTransform = nextFrame.getJointKeyFrames().get(jointName);
            JointTransform currentTransform = JointTransform.interpolate(previousTransform, nextTransform, progression);
            currentPose.put(jointName, currentTransform.getLocalTransform());
        }
        return currentPose;
    }
}
