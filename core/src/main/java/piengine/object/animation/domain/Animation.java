package piengine.object.animation.domain;

import piengine.core.base.domain.Domain;

public class Animation implements Domain<AnimationDao> {

    private final AnimationDao dao;
    private final float length;
    private final KeyFrame[] keyFrames;

    public Animation(final AnimationDao dao, final float length, final KeyFrame[] keyFrames) {
        this.dao = dao;
        this.length = length;
        this.keyFrames = keyFrames;
    }

    public float getLength() {
        return length;
    }

    public KeyFrame[] getKeyFrames() {
        return keyFrames;
    }

    @Override
    public AnimationDao getDao() {
        return dao;
    }
}
