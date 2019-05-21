package by.epam.dmitriytomashevich.javatr.courses.exceptions;

public class IllegalCommandException extends Exception {
    static final long serialVersionUID = 974616216742697308L;

    public IllegalCommandException() {
        super();
    }

    public IllegalCommandException(String message) {
        super(message);
    }

    public IllegalCommandException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalCommandException(Throwable cause) {
        super(cause);
    }

    protected IllegalCommandException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
