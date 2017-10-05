package piengine.core.utils;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import static piengine.core.utils.VectorUtils.FORWARD;
import static piengine.core.utils.VectorUtils.RIGHT;
import static piengine.core.utils.VectorUtils.UP;

public class MatrixUtils {

    private MatrixUtils() {
    }

    public static Matrix4f IDENTITY_MATRIX() {
        return new Matrix4f();
    }

    public static Matrix4f MODEL_MATRIX(final Vector3f position, final Vector3f rotation, final Vector3f scale) {
        Matrix4f matrix = new Matrix4f();
        matrix.translate(position);
        matrix.rotate((float) Math.toRadians(rotation.x), RIGHT);
        matrix.rotate((float) Math.toRadians(rotation.y), UP);
        matrix.rotate((float) Math.toRadians(rotation.z), FORWARD);
        matrix.scale(scale);

        return matrix;
    }

    public static Matrix4f VIEW_MATRIX(final Vector3f position, final Vector3f rotation) {
        Matrix4f matrix = new Matrix4f();
        Vector3f cameraPos = new Vector3f(position);

        matrix.rotate((float) Math.toRadians(-rotation.y), RIGHT);
        matrix.rotate((float) Math.toRadians(rotation.x), UP);

        matrix.translate(cameraPos.negate());

        return matrix;
    }

    public static Matrix4f VIEW_MATRIX_REVERSE(final Vector3f position, final Vector3f rotation) {
        Matrix4f matrix = new Matrix4f();
        Vector3f cameraPos = new Vector3f(position);

        matrix.translate(cameraPos.negate());

        matrix.rotate((float) Math.toRadians(-rotation.y), RIGHT);
        matrix.rotate((float) Math.toRadians(rotation.x), UP);

        return matrix;
    }

    public static Matrix4f PERSPECTIVE_PROJECTION_MATRIX(final Vector2f viewPort, final float fieldOfView, final float nearPlane, final float farPlane) {
        Matrix4f matrix = new Matrix4f();
        matrix.perspective(
                (float) Math.toRadians(fieldOfView),
                viewPort.x / viewPort.y,
                nearPlane,
                farPlane);

        return matrix;
    }

    public static Matrix4f ORTHOGRAPHIC_PROJECTION_MATRIX(final Vector2f viewPort, final float nearPlane, final float farPlane) {
        Matrix4f matrix = new Matrix4f();

        matrix.ortho(
                -viewPort.x,
                viewPort.x,
                viewPort.y,
                -viewPort.y,
                nearPlane,
                farPlane);

        return matrix;
    }

}
