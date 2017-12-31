package piengine.object.model.manager;

import piengine.object.entity.domain.Entity;
import piengine.object.model.domain.Model;
import piengine.object.model.domain.TexturedModel;
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
        return modelService.supply(file, parent);
    }

    public TexturedModel supply(final String file, final Entity entity, final Texture texture) {
        return modelService.supply(file, entity, texture);
    }
}
