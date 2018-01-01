package piengine.visual.render.domain.fragment.handler;

import piengine.core.base.exception.PIEngineException;
import piengine.visual.render.domain.fragment.domain.RenderFragmentType;

public abstract class FragmentHandler<T> {

    public abstract void handle(final T t);

    public void validate(final T t) {
        handle(t);
    }

    public abstract RenderFragmentType getType();

    static void check(final String variableName, final String validationMessage) {
        if (validationMessage != null) {
            throw new PIEngineException("Invalid render plan. %s is %s!", variableName, validationMessage);
        }
    }

    static <T> String notNull(final T value) {
        if (value != null) {
            return null;
        }
        return "null";
    }

}
