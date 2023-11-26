package by.bsuir.alekseeva.flowershop.exception;

public class DAOException extends ProjectException{
    public DAOException(String message) {
        super(message);
    }

    public DAOException(String message, Exception e) {
        super(message, e);
    }
}
