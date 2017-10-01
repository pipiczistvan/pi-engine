package piengine.core.time.manager;

import piengine.core.time.service.TimeService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

@Component
public class TimeManager {

    private final TimeService timeService;

    @Wire
    public TimeManager(final TimeService timeService) {
        this.timeService = timeService;
    }

    public int getFPS() {
        return timeService.getFPS();
    }

}
