package piengine.object.water.service;

import piengine.core.base.resource.SupplierService;
import piengine.object.water.accessor.WaterAccessor;
import piengine.object.water.domain.Water;
import piengine.object.water.domain.WaterDao;
import piengine.object.water.domain.WaterData;
import piengine.object.water.domain.WaterKey;
import piengine.object.water.interpreter.WaterInterpreter;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

@Component
public class WaterService extends SupplierService<WaterKey, WaterData, WaterDao, Water> {

    @Wire
    public WaterService(final WaterAccessor waterAccessor, final WaterInterpreter waterInterpreter) {
        super(waterAccessor, waterInterpreter);
    }

    @Override
    protected Water createDomain(final WaterDao dao, final WaterData resource) {
        return new Water(resource.parent, dao);
    }
}
