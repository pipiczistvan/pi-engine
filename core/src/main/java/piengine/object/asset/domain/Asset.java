package piengine.object.asset.domain;

import piengine.core.base.api.Initializable;
import piengine.core.base.api.Updatable;
import piengine.object.entity.domain.Entity;

public abstract class Asset<T extends AssetArgument> extends Entity implements Initializable, Updatable {

    protected T arguments;

    public void passArguments(final T arguments) {
        this.arguments = arguments;
    }

    @Override
    public void update(double delta) {
    }
}
