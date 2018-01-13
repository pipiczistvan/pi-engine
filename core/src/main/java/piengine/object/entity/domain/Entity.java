package piengine.object.entity.domain;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

import static piengine.core.utils.MatrixUtils.MODEL_MATRIX;

//todo#3 Camera translateRotate() egyszerre
//todo#4 Camera nem entity -> modelMatrix helyett viewMatrix, vagy lehessen felülírni a mátrixszámítást
//todo#5 Uniform caching

public abstract class Entity {

    private static final Vector3f ZERO = new Vector3f(0);
    private static final Vector3f ONE = new Vector3f(1);

    private final Vector3f position;
    private final Vector3f rotation;
    private final Vector3f scale;
    private final Matrix4f modelMatrix;

    private final Entity parent;
    private final List<Entity> children;

    protected Entity(final Entity parent) {
        this.position = new Vector3f(ZERO);
        this.rotation = new Vector3f(ZERO);
        this.scale = new Vector3f(ONE);
        this.modelMatrix = new Matrix4f();
        this.children = new ArrayList<>();

        this.parent = parent;
        if (parent != null) {
            parent.addChild(this);
        }
    }

    public void addChild(final Entity entity) {
        children.add(entity);
        entity.translateRotateScale(position, rotation, scale);
    }

    // POSITION
    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(final float x, final float y, final float z) {
        Vector3f parentPosition = parent == null ? ZERO : parent.position;

        position.set(x, y, z);
        position.add(parentPosition);
        updateModelMatrix();
        children.forEach(entity -> entity.setPosition(x, y, z));
    }

    public void setPosition(final Vector3f vector) {
        setPosition(vector.x, vector.y, vector.z);
    }

    public void translate(final float x, final float y, final float z) {
        position.add(x, y, z);
        updateModelMatrix();
        children.forEach(entity -> entity.translate(x, y, z));
    }

    public void translate(final Vector3f vector) {
        translate(vector.x, vector.y, vector.z);
    }

    // ROTATION
    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(final float yaw, final float pitch, final float roll) {
        Vector3f parentRotation = parent == null ? ZERO : parent.rotation;

        rotation.set(yaw, pitch, roll);
        rotation.add(parentRotation);
        updateModelMatrix();
        children.forEach(entity -> entity.setRotation(yaw, pitch, roll));
    }

    public void setRotation(final Vector3f vector) {
        setRotation(vector.x, vector.y, vector.z);
    }

    public void rotate(final float yaw, final float pitch, final float roll) {
        rotation.add(yaw, pitch, roll);
        updateModelMatrix();
        children.forEach(entity -> entity.rotate(yaw, pitch, roll));
    }

    public void rotate(final Vector3f vector) {
        rotate(vector.x, vector.y, vector.z);
    }

    // SCALE
    public Vector3f getScale() {
        return scale;
    }

    public void setScale(final float x, final float y, final float z) {
        Vector3f parentScale = parent == null ? ONE : parent.scale;

        scale.set(x, y, z);
        scale.mul(parentScale);
        updateModelMatrix();
        children.forEach(entity -> entity.setScale(x, y, z));
    }

    public void setScale(final Vector3f vector) {
        setScale(vector.x, vector.y, vector.z);
    }

    public void setScale(final float scale) {
        setScale(scale, scale, scale);
    }

    public void scale(final float x, final float y, final float z) {
        scale.mul(x, y, z);
        updateModelMatrix();
        children.forEach(entity -> entity.scale(x, y, z));
    }

    public void scale(final Vector3f vector) {
        scale(vector.x, vector.y, vector.z);
    }

    public void translateRotateScale(final Vector3f translation, final Vector3f rotation, final Vector3f scale) {
        this.position.add(translation);
        this.rotation.add(rotation);
        this.scale.mul(scale);
        updateModelMatrix();
        children.forEach(entity -> entity.translateRotateScale(translation, rotation, scale));
    }

    // MODEL MATRIX
    public Matrix4f getModelMatrix() {
        return modelMatrix;
    }

    private void updateModelMatrix() {
        MODEL_MATRIX(position, rotation, scale, modelMatrix);
    }
}
