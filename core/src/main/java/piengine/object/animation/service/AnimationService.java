package piengine.object.animation.service;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import piengine.core.base.resource.SupplierService;
import piengine.object.animation.accessor.AnimationAccessor;
import piengine.object.animation.domain.Animation;
import piengine.object.animation.domain.AnimationDao;
import piengine.object.animation.domain.AnimationData;
import piengine.object.animation.domain.AnimationKey;
import piengine.object.animation.domain.JointTransform;
import piengine.object.animation.domain.JointTransformData;
import piengine.object.animation.domain.KeyFrame;
import piengine.object.animation.domain.KeyFrameData;
import piengine.object.animation.domain.Quaternion;
import piengine.object.animation.interpreter.AnimationInterpreter;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import java.util.HashMap;
import java.util.Map;

@Component
public class AnimationService extends SupplierService<AnimationKey, AnimationData, AnimationDao, Animation> {

    @Wire
    public AnimationService(final AnimationAccessor animationAccessor, final AnimationInterpreter animationInterpreter) {
        super(animationAccessor, animationInterpreter);
    }

    @Override
    protected Animation createDomain(final AnimationDao dao, final AnimationData resource) {
        KeyFrame[] frames = new KeyFrame[resource.keyFrames.length];
        for (int i = 0; i < frames.length; i++) {
            frames[i] = createKeyFrame(resource.keyFrames[i]);
        }

        return new Animation(dao, resource.lengthSeconds, frames);
    }

    private KeyFrame createKeyFrame(final KeyFrameData data) {
        Map<String, JointTransform> map = new HashMap<>();
        for (JointTransformData jointData : data.jointTransforms) {
            JointTransform jointTransform = createTransform(jointData);
            map.put(jointData.jointNameId, jointTransform);
        }
        return new KeyFrame(data.time, map);
    }

    private JointTransform createTransform(final JointTransformData data) {
        Matrix4f mat = data.jointLocalTransform;
        Vector3f translation = new Vector3f(mat.m30(), mat.m31(), mat.m32());
        Quaternion rotation = Quaternion.fromMatrix(mat);
        return new JointTransform(translation, rotation);
    }
}
