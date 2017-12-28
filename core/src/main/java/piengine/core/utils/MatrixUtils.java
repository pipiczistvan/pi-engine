package piengine.core.utils;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector2i;
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
        MODEL_MATRIX(position, rotation, scale, matrix);

        return matrix;
    }

    public static void MODEL_MATRIX(final Vector3f position, final Vector3f rotation, final Vector3f scale, final Matrix4f destination) {
        destination.identity();
        destination.translate(position);
        destination.rotate((float) Math.toRadians(rotation.x), RIGHT);
        destination.rotate((float) Math.toRadians(rotation.y), UP);
        destination.rotate((float) Math.toRadians(rotation.z), FORWARD);
        destination.scale(scale);
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

    public static Matrix4f PERSPECTIVE_PROJECTION_MATRIX(final Vector2i viewPort, final float fieldOfView, final float nearPlane, final float farPlane) {
        Matrix4f matrix = new Matrix4f();
        matrix.perspective(
                (float) Math.toRadians(fieldOfView),
                (float) viewPort.x / (float) viewPort.y,
                nearPlane,
                farPlane);

        return matrix;
    }

    public static Matrix4f ORTHOGRAPHIC_PROJECTION_MATRIX(final Vector2i viewPort, final float farPlane) {
        Matrix4f matrix = new Matrix4f();

        matrix.ortho(
                (float) -viewPort.x,
                (float) viewPort.x,
                (float) viewPort.y,
                (float) -viewPort.y,
                -farPlane,
                farPlane);

        return matrix;
    }

}
