package piengine.object.animation.service;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import piengine.core.architecture.service.SupplierService;
import piengine.io.loader.dae.domain.ColladaDto;
import piengine.io.loader.dae.domain.JointTransform;
import piengine.io.loader.dae.domain.JointTransformData;
import piengine.io.loader.dae.domain.KeyFrame;
import piengine.io.loader.dae.domain.KeyFrameData;
import piengine.io.loader.dae.loader.ColladaLoader;
import piengine.object.animation.domain.Animation;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import java.util.HashMap;
import java.util.Map;

@Component
public class AnimationService extends SupplierService<String, Animation> {

    private final ColladaLoader colladaLoader;

    @Wire
    public AnimationService(final ColladaLoader colladaLoader) {
        this.colladaLoader = colladaLoader;
    }

    @Override
    public Animation supply(final String key) {
        ColladaDto collada = colladaLoader.load(key);
        KeyFrame[] frames = new KeyFrame[collada.animationData.keyFrames.length];
        for (int i = 0; i < frames.length; i++) {
            frames[i] = createKeyFrame(collada.animationData.keyFrames[i]);
        }

        return new Animation(collada.animationData.lengthSeconds, frames);
    }

    @Override
    public void terminate() {
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
        Quaternionf rotation = new Quaternionf().setFromNormalized(mat);
        return new JointTransform(translation, rotation);
    }
}
