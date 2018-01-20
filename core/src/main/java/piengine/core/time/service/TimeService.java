package piengine.core.time.service;

import piengine.core.base.api.Initializable;
import piengine.core.base.api.Service;
import piengine.core.time.interpreter.TimeInterpreter;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import static piengine.core.base.type.property.ApplicationProperties.get;
import static piengine.core.base.type.property.PropertyKeys.TIME_FPS_CAP;

@Component
public class TimeService implements Service, Initializable {

    private static final int FPS_CAP = get(TIME_FPS_CAP);

    private final TimeInterpreter timeInterpreter;

    private double loopSlot;
    private boolean waitTimeSpent;

    private double lastTime;

    private double delta;
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

        this.delta = 0;
        this.totalFrameTime = 0;

        this.frameCount = 0;
        this.totalFrameCount = 0;
    }

    public void update() {
        updateDelta(timeInterpreter.getTime());

        waitTimeSpent = delta >= loopSlot;
        if (!waitTimeSpent) {
            timeInterpreter.sleep();
        }

        if (totalFrameTime > 1) {
            totalFrameCount = frameCount;
            frameCount = 0;
            totalFrameTime = 0;
        }
    }

    public void frameUpdated() {
        delta = 0;
        frameCount++;
    }

    public int getFPS() {
        return totalFrameCount;
    }

    public float getDelta() {
        return (float) delta;
    }

    public boolean waitTimeSpent() {
        return waitTimeSpent;
    }

    private void updateDelta(final double currentTime) {
        double deltaTime = currentTime - lastTime;
        delta += deltaTime;
        totalFrameTime += deltaTime;
        lastTime = currentTime;
    }
}
