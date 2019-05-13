package by.epam.dmitriytomashevich.javatr.courses.logic.exception;

public class LogicException extends Exception {
    static final long serialVersionUID = 2406977684014757860L;
    public LogicException(String message) {
        super(message);
    }

    public LogicException(String message, Throwable cause) {
        super(message, cause);
    }

    public LogicException(Throwable cause) {
        super(cause);
    }

    protected LogicException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
