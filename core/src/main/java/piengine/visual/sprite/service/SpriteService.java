package piengine.visual.sprite.service;

import piengine.visual.image.domain.Image;
import piengine.visual.image.manager.ImageManager;
import piengine.visual.sprite.domain.Sprite;
import piengine.visual.sprite.domain.SpriteDao;
import piengine.visual.sprite.domain.SpriteKey;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

@Component
public class SpriteService {

    private final ImageManager imageManager;

    @Wire
    public SpriteService(ImageManager imageManager) {
        this.imageManager = imageManager;
    }

    //todo: temporary
    public Sprite supply(final SpriteKey key) {
        Image spriteImage = imageManager.supply(key.file);
        SpriteDao dao = new SpriteDao(spriteImage.getDao().getTexture());

        return new Sprite(dao, spriteImage.getSize(), key.numberOfRows);
    }

}
