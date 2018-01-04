package piengine.core.time.service;

import piengine.core.base.api.Initializable;
import piengine.core.base.api.Service;
import piengine.core.base.api.Updatable;
import piengine.core.time.interpreter.TimeInterpreter;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import static piengine.core.base.type.property.ApplicationProperties.get;
import static piengine.core.base.type.property.PropertyKeys.TIME_FPS_CAP;

@Component
public class TimeService implements Service, Initializable, Updatable {

    private static final int FPS_CAP = get(TIME_FPS_CAP);

    private final TimeInterpreter timeInterpreter;

    private double loopSlot;
    private boolean waitTimeSpent;

    private double lastTime;
    private double deltaTime;

    private double totalDeltaTime;
    private double totalFrameTime;

    private int frameCount;
    private int totalFrameCount;

    @Wire
    public TimeService(final TimeInterpreter timeInterpreter) {
        this.timeInterpreter = timeInterpreter;
    }

    @Override
    public void initialize() {
        this.loopSlot = 1d / FPS_CAP;
        this.waitTimeSpent = false;

        this.lastTime = timeInterpreter.getTime();
        this.deltaTime = 0;

        this.totalDeltaTime = 0;
        this.totalFrameTime = 0;

        this.frameCount = 0;
        this.totalFrameCount = 0;
    }

    @Override
    public void update(double delta) {
        updateDelta(timeInterpreter.getTime());

        if (totalDeltaTime < loopSlot) {
            waitTimeSpent = false;
            timeInterpreter.sleep();
        } else {
            waitTimeSpent = true;
            totalDeltaTime = 0;
            frameCount++;
        }
        if (totalFrameTime > 1) {
            totalFrameCount = frameCount;
            frameCount = 0;
            totalFrameTime = 0;
        }
    }

    public int getFPS() {
        return totalFrameCount;
    }

    public double getDelta() {
        return deltaTime;
    }

    public boolean waitTimeSpent() {
        return waitTimeSpent;
    }

    private void updateDelta(double currentTime) {
        deltaTime = currentTime - lastTime;
        totalDeltaTime += deltaTime;
        totalFrameTime += deltaTime;
        lastTime = currentTime;
    }

}
