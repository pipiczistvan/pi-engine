package piengine.object.animation.domain;

import java.util.ArrayList;
import java.util.List;

public class KeyFrameData {

    public final float time;
    public final List<JointTransformData> jointTransforms;

    public KeyFrameData(final float time) {
        this.time = time;
        this.jointTransforms = new ArrayList<>();
    }

    public void addJointTransform(final JointTransformData transform) {
        jointTransforms.add(transform);
    }
}
