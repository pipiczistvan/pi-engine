package piengine.object.animatedmodel.domain;

import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.List;

public class Joint {

    public final int index;
    public final String name;
    public final List<Joint> children;

    private final Matrix4f animatedTransform;
    private final Matrix4f localBindTransform;
    private final Matrix4f inverseBindTransform;

    public Joint(final int index, final String name, final Matrix4f bindLocalTransform) {
        this.index = index;
        this.name = name;
        this.localBindTransform = bindLocalTransform;

        this.children = new ArrayList<>();
        this.animatedTransform = new Matrix4f();
        this.inverseBindTransform = new Matrix4f();
    }

    public void addChild(final Joint child) {
        this.children.add(child);
    }

    public Matrix4f getAnimatedTransform() {
        return animatedTransform;
    }

    public void setAnimationTransform(final Matrix4f animationTransform) {
        this.animatedTransform.set(animationTransform);
    }

    public Matrix4f getInverseBindTransform() {
        return inverseBindTransform;
    }

    protected void calcInverseBindTransform(final Matrix4f parentBindTransform) {
        Matrix4f bindTransform = new Matrix4f(parentBindTransform).mul(localBindTransform);
        bindTransform.invert(inverseBindTransform);
        for (Joint child : children) {
            child.calcInverseBindTransform(bindTransform);
        }
    }
}
