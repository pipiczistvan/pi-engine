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
import piengine.visual.framebuffer.domain.Framebuffer;
import piengine.visual.light.Light;

import static piengine.core.base.type.property.ApplicationProperties.get;
import static piengine.core.base.type.property.PropertyKeys.CAMERA_NEAR_PLANE;
import static piengine.core.base.type.property.PropertyKeys.SHADOW_DISTANCE;
import static piengine.core.base.type.property.PropertyKeys.SHADOW_OFFSET;
import static piengine.visual.camera.domain.ProjectionType.ORTHOGRAPHIC;

public class Shadow implements Domain<ShadowDao>, Updatable {


    private static final Matrix4f OFFSET_MATRIX = createOffset();
    private static final float OFFSET = get(SHADOW_OFFSET);
    private static final float SHADOW_MAX_DISTANCE = get(SHADOW_DISTANCE);
    private static final float SHADOW_MIN_DISTANCE = get(CAMERA_NEAR_PLANE);
    private static final Vector4f UP = new Vector4f(0, 1, 0, 0);
    private static final Vector4f FORWARD = new Vector4f(0, 0, -1, 0);

    private final ShadowDao dao;
    private final Camera playerCamera;
    private final Light light;
    private final Vector2i lightViewport;

    public final Framebuffer shadowMap;
    public final Matrix4f shadowMapSpaceMatrix;
    public final Camera lightCamera;

    private float farHeight, farWidth, nearHeight, nearWidth;
    private float maxX = 0;
    private float minX = 0;
    private float maxY = 0;
    private float minY = 0;
    private float maxZ = 0;
    private float minZ = 0;

    public Shadow(final ShadowDao dao, final Camera playerCamera, final Light light, final Framebuffer shadowMap) {
        this.dao = dao;
        this.playerCamera = playerCamera;
        this.light = light;
        this.shadowMap = shadowMap;

        this.shadowMapSpaceMatrix = new Matrix4f();
        this.lightViewport = new Vector2i(0);
        this.lightCamera = new FirstPersonCamera(null, lightViewport, new CameraAttribute(0, -30, 20), ORTHOGRAPHIC);
        calculateWidthsAndHeights();
    }

    @Override
    public void update(double delta) {
        // PROJECTION UPDATE
        Matrix4f rotation = calculateCameraRotationMatrix(playerCamera.getRotation());
        Vector4f forwardVector = new Vector4f(0, 0, 0, 1);
        rotation.transform(FORWARD, forwardVector);

        Vector3f toFar = new Vector3f(forwardVector.x, forwardVector.y, forwardVector.z);
        toFar.mul(SHADOW_MAX_DISTANCE);
        Vector3f toNear = new Vector3f(forwardVector.x, forwardVector.y, forwardVector.z);
        toNear.mul(SHADOW_MIN_DISTANCE);
        Vector3f centerNear = new Vector3f();
        toNear.add(playerCamera.getPosition(), centerNear);
        Vector3f centerFar = new Vector3f();
        toFar.add(playerCamera.getPosition(), centerFar);

        Vector4f[] points = calculateFrustumVertices(rotation, new Vector3f(forwardVector.x, forwardVector.y, forwardVector.z), centerNear, centerFar);

        boolean first = true;
        for (Vector4f point : points) {
            if (first) {
                minX = point.x;
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

        // VIEW UPDATE
        Vector3f lightDirection = new Vector3f(light.getPosition()).negate().normalize();

        float yaw = (float) Math.toDegrees(((float) Math.atan(lightDirection.x / -lightDirection.z)));
        float pitch = (float) Math.toDegrees(Math.asin(lightDirection.y));

        Vector3f center = getCenter(lightCamera.getView());

        lightCamera.setPositionRotation(center.x, center.y, center.z, yaw, pitch, 0);

        lightCamera.getProjection().mul(lightCamera.getView(), shadowMapSpaceMatrix);
        OFFSET_MATRIX.mul(shadowMapSpaceMatrix, shadowMapSpaceMatrix);
    }

    private static Matrix4f createOffset() {
        Matrix4f offset = new Matrix4f();
        offset.translate(new Vector3f(0.5f, 0.5f, 0.5f));
        offset.scale(new Vector3f(0.5f, 0.5f, 0.5f));
        return offset;
    }

    private Vector3f getCenter(Matrix4f lightView) {
        float x = (minX + maxX) / 2f;
        float y = (minY + maxY) / 2f;
        float z = (minZ + maxZ) / 2f;
        Vector4f cen = new Vector4f(x, y, z, 1);
        Matrix4f invertedLight = new Matrix4f();
        lightView.invert(invertedLight);
        invertedLight.transform(cen);
        return new Vector3f(cen.x, cen.y, cen.z);
    }

    @Override
    public ShadowDao getDao() {
        return dao;
    }

    private Vector4f[] calculateFrustumVertices(Matrix4f rotation, Vector3f forwardVector,
                                                Vector3f centerNear, Vector3f centerFar) {
        Vector4f upVector = new Vector4f(0, 0, 0, 1);
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
        Matrix4f lightViewMatrix = lightCamera.getView();
        lightViewMatrix.transform(point4f, point4f);
        return point4f;
    }

    private Matrix4f calculateCameraRotationMatrix(final Vector3f cameraRotation) {
        Matrix4f rotation = new Matrix4f();
        rotation.rotate((float) Math.toRadians(cameraRotation.x), new Vector3f(1, 0, 0));
        rotation.rotate((float) Math.toRadians(cameraRotation.y), new Vector3f(0, 1, 0));
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
