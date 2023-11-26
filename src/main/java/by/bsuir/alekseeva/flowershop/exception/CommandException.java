package by.bsuir.alekseeva.flowershop.exception;

public class CommandException extends ProjectException {
    public CommandException(String message) {
        super(message);
    }

    public CommandException(String message, Exception innerException) {
        super(message, innerException);
    }
}