package piengine.visual.render.domain.fragment;

import piengine.visual.render.domain.fragment.domain.RenderFragmentType;

public class RenderFragment<T> {

    public final RenderFragmentType type;
    public final T value;

    public RenderFragment(RenderFragmentType type, T value) {
        this.type = type;
        this.value = value;
    }

    public RenderFragment(RenderFragmentType type) {
        this(type, null);
    }

}
