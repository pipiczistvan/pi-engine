package piengine.core.base.exception;

public class PIEngineException extends RuntimeException {

    public PIEngineException(final String message) {
        super(message);
    }

    public PIEngineException(final String message, final Object... args) {
        super(formatMessage(message, args));
    }

    public PIEngineException(final Throwable cause) {
        super(cause);
    }

    public PIEngineException(final Throwable cause, final String message) {
        super(message, cause);
    }

    public PIEngineException(final Throwable cause, final String message, final Object... args) {
        super(formatMessage(message, args), cause);
    }

    private static String formatMessage(final String message, final Object... args) {
        return String.format(message, formatArgs(args));
    }

    private static Object[] formatArgs(final Object... args) {
        String[] formattedArgs = new String[args.length];

        for (int i = 0; i < args.length; i++) {
            formattedArgs[i] = String.format("%s%s%s", "[", args[i], "]");
        }

        return formattedArgs;
    }
}
