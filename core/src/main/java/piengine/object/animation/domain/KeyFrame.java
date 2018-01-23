package piengine.object.animation.domain;

import java.util.Map;

public class KeyFrame {

    private final float timeStamp;
    private final Map<String, JointTransform> pose;

    public KeyFrame(final float timeStamp, final Map<String, JointTransform> jointKeyFrames) {
        this.timeStamp = timeStamp;
        this.pose = jointKeyFrames;
    }

    public float getTimeStamp() {
        return timeStamp;
    }

    public Map<String, JointTransform> getJointKeyFrames() {
        return pose;
    }
}
