package piengine.object.animatedmodel.service;

import piengine.core.base.resource.EntitySupplierService;
import piengine.object.animatedmodel.accessor.AnimatedModelAccessor;
import piengine.object.animatedmodel.domain.AnimatedModel;
import piengine.object.animatedmodel.domain.AnimatedModelDao;
import piengine.object.animatedmodel.domain.AnimatedModelData;
import piengine.object.animatedmodel.domain.AnimatedModelKey;
import piengine.object.animatedmodel.domain.Joint;
import piengine.object.animatedmodel.domain.JointData;
import piengine.object.animatedmodel.interpreter.AnimatedModelInterpreter;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

@Component
public class AnimatedModelService extends EntitySupplierService<AnimatedModelKey, AnimatedModelData, AnimatedModelDao, AnimatedModel> {

    @Wire
    public AnimatedModelService(final AnimatedModelAccessor animatedModelAccessor, final AnimatedModelInterpreter animatedModelInterpreter) {
        super(animatedModelAccessor, animatedModelInterpreter);
    }

    @Override
    protected AnimatedModel createDomain(final AnimatedModelDao dao, final AnimatedModelData resource) {
        Joint rootJoint = createJointHierarchy(resource.joints.headJoint);

        return new AnimatedModel(resource.parent, dao, resource.texture, rootJoint, resource.joints.jointCount);
    }

    private Joint createJointHierarchy(final JointData jointData) {
        Joint joint = new Joint(jointData.index, jointData.nameId, jointData.bindLocalTransform);
        for (JointData child : jointData.children) {
            joint.addChild(createJointHierarchy(child));
        }
        return joint;
    }
}
