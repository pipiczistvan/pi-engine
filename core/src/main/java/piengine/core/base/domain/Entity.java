package piengine.core.base.domain;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

import static piengine.core.utils.MatrixUtils.MODEL_MATRIX;

public abstract class Entity {

    private static final Vector3f ZERO = new Vector3f(0);
    private static final Vector3f ONE = new Vector3f(1);

    private final Vector3f position;
    private final Vector3f rotation;
    private final Vector3f scale;
    private final Matrix4f transformation;

    private final Entity parent;
    private final List<Entity> children;

    protected Entity(final Entity parent) {
        this.position = new Vector3f(ZERO);
        this.rotation = new Vector3f(ZERO);
        this.scale = new Vector3f(ONE);
        this.transformation = new Matrix4f();
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
    protected Vector3f getProtectedPosition() {
        return position;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(final Vector3f vector) {
        setPosition(vector.x, vector.y, vector.z);
    }

    public void setPosition(final float x, final float y, final float z) {
        translate(x - position.x, y - position.y, z - position.z);
    }

    public void translate(final float x, final float y, final float z) {
        position.add(x, y, z);
        updateTransformation(transformation);
        children.forEach(entity -> entity.translate(x, y, z));
    }

    public void translate(final Vector3f vector) {
        translate(vector.x, vector.y, vector.z);
    }

    // ROTATION
    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(final Vector3f vector) {
        setRotation(vector.x, vector.y, vector.z);
    }

    public void setRotation(final float yaw, final float pitch, final float roll) {
        rotate(yaw - rotation.x, pitch - rotation.y, roll - rotation.z);
    }

    public void rotate(final float yaw, final float pitch, final float roll) {
        rotation.add(yaw, pitch, roll);
        updateTransformation(transformation);
        children.forEach(entity -> entity.rotate(yaw, pitch, roll));
    }

    public void rotate(final Vector3f vector) {
        rotate(vector.x, vector.y, vector.z);
    }

    // SCALE
    public Vector3f getScale() {
        return scale;
    }

    public void setScale(final float scale) {
        setScale(scale, scale, scale);
    }

    public void setScale(final float x, final float y, final float z) {
        scale(x / scale.x, y / scale.y, z / scale.z);
    }

    public void setScale(final Vector3f vector) {
        setScale(vector.x, vector.y, vector.z);
    }

    public void scale(final float x, final float y, final float z) {
        scale.mul(x, y, z);
        updateTransformation(transformation);
        children.forEach(entity -> entity.scale(x, y, z));
    }

    public void scale(final Vector3f vector) {
        scale(vector.x, vector.y, vector.z);
    }

    // MULTI
    public void setPositionRotation(final float x, final float y, final float z,
                                    final float yaw, final float pitch, final float roll) {
        translateRotate(
                x - position.x, y - position.y, z - position.z,
                yaw - rotation.x, pitch - rotation.y, roll - rotation.z
        );
    }

    public void setPositionRotation(final Vector3f position, final Vector3f rotation) {
        setPositionRotation(position.x, position.y, position.z, rotation.x, rotation.y, rotation.z);
    }

    public void translateRotate(final float x, final float y, final float z,
                                final float yaw, final float pitch, final float roll) {
        translateRotateScale(x, y, z, yaw, pitch, roll, 1, 1, 1);
    }

    public void translateRotate(final Vector3f translation, final Vector3f rotation) {
        translateRotate(translation.x, translation.y, translation.z, rotation.x, rotation.y, rotation.z);
    }

    public void translateRotateScale(final Vector3f translation, final Vector3f rotation, final Vector3f scale) {
        translateRotateScale(
                translation.x, translation.y, translation.z,
                rotation.x, rotation.y, rotation.z,
                scale.x, scale.y, scale.z
        );
    }

    public void translateRotateScale(final float x, final float y, final float z,
                                     final float yaw, final float pitch, final float roll,
                                     final float width, final float height, final float depth) {
        this.position.add(x, y, z);
        this.rotation.add(yaw, pitch, roll);
        this.scale.mul(width, height, depth);
        updateTransformation(transformation);
        children.forEach(entity -> entity.translateRotateScale(x, y, z, yaw, pitch, roll, width, height, depth));
    }

    // MODEL MATRIX
    public Matrix4f getTransformation() {
        return transformation;
    }

    protected void updateTransformation(final Matrix4f transformation) {
        MODEL_MATRIX(position, rotation, scale, transformation);
    }

    private Vector3f getParentPosition() {
        return parent == null ? ZERO : parent.position;
    }

    private Vector3f getParentRotation() {
        return parent == null ? ZERO : parent.rotation;
    }

    private Vector3f getParentScale() {
        return parent == null ? ONE : parent.scale;
    }
}
