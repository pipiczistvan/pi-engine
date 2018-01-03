package piengine.object.entity.domain;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import static piengine.core.utils.MatrixUtils.MODEL_MATRIX;

public abstract class Entity {

    protected Entity parent;

    private final Vector3f position;
    private final Vector3f rotation;
    private final Vector3f scale;
    private final Matrix4f modelMatrix;

    protected Entity() {
        this.position = new Vector3f();
        this.rotation = new Vector3f();
        this.scale = new Vector3f(1);
        this.modelMatrix = new Matrix4f();
    }

    public Entity(final Entity parent) {
        this();

        setParent(parent);
    }

    public void setParent(final Entity parent) {
        this.parent = parent;
    }

    public Matrix4f getModelMatrix() {
        MODEL_MATRIX(position, rotation, scale, modelMatrix);
        if (parent != null) {
            parent.getModelMatrix().mul(modelMatrix, modelMatrix);
        }

        return modelMatrix;
    }

    public Vector3f getPosition() {
        Vector3f relativePosition = new Vector3f(this.position);
        if (parent != null) {
            this.position.add(parent.position, relativePosition);
        }

        return relativePosition;
    }

    public void setPosition(float x, float y, float z) {
        this.position.set(x, y, z);
    }

    public void addPosition(float x, float y, float z) {
        this.position.add(x, y, z);
    }

    public Vector3f getScale() {
        Vector3f relativeScale = new Vector3f(this.scale);
        if (parent != null) {
            this.scale.mul(parent.scale, relativeScale);
        }

        return relativeScale;
    }

    @SuppressWarnings("SuspiciousNameCombination")
    public void setScale(float x) {
        setScale(x, x, x);
    }

    public void setScale(float x, float y, float z) {
        this.scale.set(x, y, z);
    }

    public Vector3f getRotation() {
        Vector3f relativeRotation = new Vector3f(this.rotation);
        if (parent != null) {
            this.rotation.add(parent.rotation, relativeRotation);
        }

        return relativeRotation;
    }

    public void setRotation(float x, float y, float z) {
        this.rotation.set(x, y, z);
    }

    public void addRotation(float x, float y, float z) {
        this.rotation.add(x, y, z);
    }

}
