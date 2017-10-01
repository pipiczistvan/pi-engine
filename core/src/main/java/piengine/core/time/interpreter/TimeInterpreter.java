package piengine.core.time.interpreter;

import piengine.core.base.exception.PIEngineException;
import puppeteer.annotation.premade.Component;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

@Component
public class TimeInterpreter {

    public double getTime() {
        return glfwGetTime();
    }

    public void sleep() {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            throw new PIEngineException(e);
        }
    }

}
