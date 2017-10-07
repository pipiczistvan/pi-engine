package piengine.object.entity.domain;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import static piengine.core.utils.MatrixUtils.MODEL_MATRIX;

public abstract class Entity {

    protected Entity parent;
    protected final Vector3f position;
    protected final Vector3f rotation;
    protected final Vector3f scale;

    protected Entity() {
        this.position = new Vector3f();
        this.rotation = new Vector3f();
        this.scale = new Vector3f(1);
    }

    public Entity(final Entity parent) {
        this();

        this.position.add(parent.position);
        this.rotation.add(parent.rotation);
        this.position.mul(parent.scale);

        setParent(parent);
    }

    public void setParent(final Entity parent) {
        this.parent = parent;
    }

    public Matrix4f getModelMatrix() {
        Matrix4f modelMatrix = MODEL_MATRIX(position, rotation, scale);
        if (parent != null) {
            parent.getModelMatrix().mul(modelMatrix, modelMatrix);
        }

        return modelMatrix;
    }

    public Vector3f getPosition() {
        Vector3f relativePosition = new Vector3f();
        this.position.add(parent.position, relativePosition);

        return relativePosition;
    }

    public void setPosition(float x, float y, float z) {
        this.position.set(x, y, z);
    }

    public void setScale(float x) {
        this.scale.set(x);
    }

    public void setScale(float x, float y, float z) {
        this.scale.set(x, y, z);
    }

    public Vector3f getRotation() {
        Vector3f relativeRotation = new Vector3f();
        this.rotation.add(parent.rotation, relativeRotation);

        return relativeRotation;
    }

    public void setRotation(float x, float y, float z) {
        this.rotation.set(x, y, z);
    }

    public void addRotation(float x, float y, float z) {
        this.rotation.add(x, y, z);
    }

}
