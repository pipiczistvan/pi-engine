package piengine.object.model.manager;

import org.joml.Vector4f;
import piengine.object.entity.domain.Entity;
import piengine.object.model.domain.Model;
import piengine.object.model.domain.ModelAttribute;
import piengine.object.model.service.ModelService;
import piengine.visual.texture.domain.Texture;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

@Component
public class ModelManager {

    private final ModelService modelService;

    @Wire
    public ModelManager(final ModelService modelService) {
        this.modelService = modelService;
    }

    public Model supply(final String file, final Entity parent) {
        return supply(file, parent, new Vector4f(1));
    }

    public Model supply(final String file, final Entity parent, final Texture texture) {
        return supply(file, parent, texture, new Vector4f(1));
    }

    public Model supply(final String file, final Entity parent, final Vector4f color) {
        return supply(file, parent, null, color);
    }

    public Model supply(final String file, final Entity parent, final Texture texture, final Vector4f color) {
        return modelService.supply(file, parent, new ModelAttribute(texture, color));
    }
}
