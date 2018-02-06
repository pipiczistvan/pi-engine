package piengine.io.loader.dae.domain;

import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.List;

public class JointData {

    public final int index;
    public final String nameId;
    public final Matrix4f bindLocalTransform;
    public final List<JointData> children;

    public JointData(final int index, final String nameId, final Matrix4f bindLocalTransform) {
        this.index = index;
        this.nameId = nameId;
        this.bindLocalTransform = bindLocalTransform;
        this.children = new ArrayList<>();
    }

    public void addChild(final JointData child) {
        children.add(child);
    }
}
