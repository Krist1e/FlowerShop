package by.bsuir.alekseeva.flowershop.exception;

public class CommandFactoryException extends ProjectException {
    public CommandFactoryException(String message) {
        super(message);
    }

    public CommandFactoryException(String message, Exception innerException) {
        super(message, innerException);
    }
}
