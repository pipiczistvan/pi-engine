package piengine.visual.shadow.domain;

import org.joml.Matrix4f;
import org.joml.Vector2i;
import org.joml.Vector3f;
import org.joml.Vector4f;
import piengine.core.base.api.Updatable;
import piengine.core.base.domain.Domain;
import piengine.visual.camera.domain.Camera;
import piengine.visual.camera.domain.CameraAttribute;
import piengine.visual.camera.domain.FirstPersonCamera;
import piengine.visual.framebuffer.domain.FrameBuffer;
import piengine.visual.light.Light;

import static piengine.visual.camera.domain.ProjectionType.ORTHOGRAPHIC;

public class Shadow implements Domain<ShadowDao>, Updatable {

    //todo: temporary(dinamikus)
    private static final float OFFSET = 10;
    private static final float SHADOW_MAX_DISTANCE = 100;
    private static final float SHADOW_MIN_DISTANCE = 0.1f;//todo: camera nearplane
    private static final Vector4f UP = new Vector4f(0, 1, 0, 0);
    private static final Vector4f FORWARD = new Vector4f(0, 0, -1, 0);

    private final ShadowDao dao;
    private final Camera playerCamera;
    private final Light light;
    private final Vector2i lightViewport;

    public final FrameBuffer shadowMap;
    public final Camera lightCamera;

    private float farHeight, farWidth, nearHeight, nearWidth;
    float maxX = 0;
    float minX = 0;
    float maxY = 0;
    float minY = 0;
    float maxZ = 0;
    float minZ = 0;

    public Shadow(final ShadowDao dao, final Camera playerCamera, final Light light, final FrameBuffer shadowMap) {
        this.dao = dao;
        this.playerCamera = playerCamera;
        this.light = light;
        this.shadowMap = shadowMap;

        this.lightViewport = new Vector2i(50, 50);
        this.lightCamera = new FirstPersonCamera(null, lightViewport, new CameraAttribute(0, -30, 20), ORTHOGRAPHIC);
        //todo: biztos csak 1szer kell?
        calculateWidthsAndHeights();
    }

    @Override
    public void update(double delta) {
        // VIEW UPDATE
        Vector3f lightPosition = light.getPosition();
        Vector3f lightDirection = new Vector3f(lightPosition).negate().normalize();

        float yaw = (float) Math.toDegrees(((float) Math.atan(lightDirection.x / -lightDirection.z)));
        float pitch = (float) Math.toDegrees(Math.asin(lightDirection.y));

//        lightCamera.setPosition(lightPosition.x, lightPosition.y, lightPosition.z);
        lightCamera.setRotation(yaw, pitch, 0);

        //todo: farplane, nearplane, viewport.x, viewport.y
        // PROJECTION UPDATE
        Vector3f cameraPosition = playerCamera.getPosition();
        Vector3f cameraRotation = playerCamera.getRotation();

        Matrix4f rotation = calculateCameraRotationMatrix(cameraRotation);
        Vector4f forwardVector = new Vector4f();
        rotation.transform(FORWARD, forwardVector);

        Vector3f toFar = new Vector3f(forwardVector.x, forwardVector.y, forwardVector.z);
        toFar.mul(SHADOW_MAX_DISTANCE);
        Vector3f toNear = new Vector3f(forwardVector.x, forwardVector.y, forwardVector.z);
        toNear.mul(SHADOW_MIN_DISTANCE);
        Vector3f centerNear = new Vector3f();
        toNear.add(cameraPosition, centerNear);
        Vector3f centerFar = new Vector3f();
        toFar.add(cameraPosition, centerFar);

        Vector4f[] points = calculateFrustumVertices(rotation, new Vector3f(forwardVector.x, forwardVector.y, forwardVector.z), centerNear, centerFar);

        boolean first = true;
        for (Vector4f point : points) {
            if (first) {
                lightViewport.x = (int) point.x;
                maxX = point.x;
                minY = point.y;
                maxY = point.y;
                minZ = point.z;
                maxZ = point.z;
                first = false;
                continue;
            }
            if (point.x > maxX) {
                maxX = point.x;
            } else if (point.x < minX) {
                minX = point.x;
            }
            if (point.y > maxY) {
                maxY = point.y;
            } else if (point.y < minY) {
                minY = point.y;
            }
            if (point.z > maxZ) {
                maxZ = point.z;
            } else if (point.z < minZ) {
                minZ = point.z;
            }
        }
        maxZ += OFFSET;
        lightViewport.x = (int) (maxX - minX) / 2;
        lightViewport.y = (int) (maxY - minY) / 2;
        lightCamera.attribute.nearPlane = -(maxZ - minZ) / 2f;
        lightCamera.attribute.farPlane = (maxZ - minZ) / 2f;

        lightCamera.recalculateProjection();


        lightCamera.setPosition((maxX + minX) / 2f, (maxY + minY) / 2f, (maxZ + minZ) / 2f);
    }

    @Override
    public ShadowDao getDao() {
        return dao;
    }

    private Vector4f[] calculateFrustumVertices(Matrix4f rotation, Vector3f forwardVector,
                                                Vector3f centerNear, Vector3f centerFar) {
        Vector4f upVector = new Vector4f();
        rotation.transform(UP, upVector);

        Vector3f rightVector = new Vector3f();
        forwardVector.cross(new Vector3f(upVector.x, upVector.y, upVector.z), rightVector);

        Vector3f downVector = new Vector3f(-upVector.x, -upVector.y, -upVector.z);
        Vector3f leftVector = new Vector3f(-rightVector.x, -rightVector.y, -rightVector.z);

        Vector3f farTop = new Vector3f();
        centerFar.add(upVector.x * farHeight, upVector.y * farHeight, upVector.z * farHeight, farTop);

        Vector3f farBottom = new Vector3f();
        centerFar.add(downVector.x * farHeight, downVector.y * farHeight, downVector.z * farHeight, farBottom);

        Vector3f nearTop = new Vector3f();
        centerNear.add(upVector.x * nearHeight, upVector.y * nearHeight, upVector.z * nearHeight, nearTop);

        Vector3f nearBottom = new Vector3f();
        centerNear.add(downVector.x * nearHeight, downVector.y * nearHeight, downVector.z * nearHeight, nearBottom);

        Vector4f[] points = new Vector4f[8];
        points[0] = calculateLightSpaceFrustumCorner(farTop, rightVector, farWidth);
        points[1] = calculateLightSpaceFrustumCorner(farTop, leftVector, farWidth);
        points[2] = calculateLightSpaceFrustumCorner(farBottom, rightVector, farWidth);
        points[3] = calculateLightSpaceFrustumCorner(farBottom, leftVector, farWidth);
        points[4] = calculateLightSpaceFrustumCorner(nearTop, rightVector, nearWidth);
        points[5] = calculateLightSpaceFrustumCorner(nearTop, leftVector, nearWidth);
        points[6] = calculateLightSpaceFrustumCorner(nearBottom, rightVector, nearWidth);
        points[7] = calculateLightSpaceFrustumCorner(nearBottom, leftVector, nearWidth);
        return points;
    }

    private Vector4f calculateLightSpaceFrustumCorner(Vector3f startPoint, Vector3f direction, float width) {
        Vector3f point = new Vector3f();
        startPoint.add(direction.x * width, direction.y * width, direction.z * width, point);

        Vector4f point4f = new Vector4f(point.x, point.y, point.z, 1f);
        Matrix4f lightViewMatrix = lightCamera.getView();//todo: redundáns
        lightViewMatrix.transform(point4f, point4f);
        return point4f;
    }

    private Matrix4f calculateCameraRotationMatrix(final Vector3f cameraRotation) {
        Matrix4f rotation = new Matrix4f();
        //todo: lehet rossz :(
        rotation.rotate((float) Math.toRadians(-cameraRotation.y), new Vector3f(0, 1, 0));
        rotation.rotate((float) Math.toRadians(-cameraRotation.x), new Vector3f(1, 0, 0));
        return rotation;
    }

    private void calculateWidthsAndHeights() {
        farWidth = (float) (SHADOW_MAX_DISTANCE * Math.tan(Math.toRadians(playerCamera.attribute.fieldOfView)));
        nearWidth = (float) (playerCamera.attribute.nearPlane * Math.tan(Math.toRadians(playerCamera.attribute.fieldOfView)));
        farHeight = farWidth / getAspectRatio();
        nearHeight = nearWidth / getAspectRatio();
    }

    private float getAspectRatio() {
        return (float) playerCamera.viewport.x / (float) playerCamera.viewport.y;
    }
}
