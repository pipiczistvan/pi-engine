package piengine.core.time.interpreter;

import piengine.core.base.exception.PIEngineException;
import puppeteer.annotation.premade.Component;

@Component
public class TimeInterpreter {

    public double getTime() {
        return System.currentTimeMillis() % 1000;
    }

    public void sleep() {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            throw new PIEngineException(e);
        }
    }

}
