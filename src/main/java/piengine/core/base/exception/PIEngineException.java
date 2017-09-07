package piengine.core.base.exception;

public class PIEngineException extends RuntimeException {

    public PIEngineException(String message) {
        super(message);
    }

    public PIEngineException(String message, Object... args) {
        super(formatMessage(message, args));
    }

    public PIEngineException(Throwable cause) {
        super(cause);
    }

    public PIEngineException(Throwable cause, String message) {
        super(message, cause);
    }

    public PIEngineException(Throwable cause, String message, Object... args) {
        super(formatMessage(message, args), cause);
    }

    private static String formatMessage(String message, Object... args) {
        return String.format(message, formatArgs(args));
    }

    private static Object[] formatArgs(Object... args) {
        String[] formattedArgs = new String[args.length];

        for (int i = 0; i < args.length; i++) {
            formattedArgs[i] = String.format("%s%s%s", "[", args[i], "]");
        }

        return formattedArgs;
    }

}
