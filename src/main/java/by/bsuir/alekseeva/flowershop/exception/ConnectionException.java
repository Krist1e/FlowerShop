package by.bsuir.alekseeva.flowershop.exception;

import java.sql.SQLException;

public class ConnectionException extends SQLException {
    public ConnectionException(String message) {
        super(message);
    }

    public ConnectionException(String message, Exception innerException) {
        super(message, innerException);
    }
}
