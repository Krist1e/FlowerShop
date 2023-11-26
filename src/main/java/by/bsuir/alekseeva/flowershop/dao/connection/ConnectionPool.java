package by.bsuir.alekseeva.flowershop.dao.connection;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class ConnectionPool {
    private static final ReentrantLock lock = new ReentrantLock();
    private final ArrayBlockingQueue<Connection> usedConnections;
    private final ArrayBlockingQueue<Connection> freeConnections;
    private volatile static ConnectionPool connectionPool;

    public static ConnectionPool getInstance() {
        try {
            lock.lock();
            if (connectionPool == null) {
                connectionPool = new ConnectionPool();
            }
        } catch (Exception e) {
            log.error("Can not get Instance", e);
            throw new RuntimeException("Can not get Instance", e);
        } finally {
            lock.unlock();
        }
        return connectionPool;
    }

    private ConnectionPool() {
        try {
            ResourceBundle resourceBundle = ResourceBundle.getBundle("database", Locale.getDefault());
            String driverName = resourceBundle.getString("db.driver");
            String url = resourceBundle.getString("db.url");
            String user = resourceBundle.getString("db.user");
            String password = resourceBundle.getString("db.password");
            int poolSize = Integer.parseInt(resourceBundle.getString("db.poolSize"));
            freeConnections = new ArrayBlockingQueue<>(poolSize);
            usedConnections = new ArrayBlockingQueue<>(poolSize);
            Class.forName(driverName);
            Properties properties = new Properties();
            properties.put("user", user);
            properties.put("password", password);
            properties.put("autoReconnect", "true");
            properties.put("characterEncoding", "UTF-8");
            properties.put("useUnicode", "true");
            for (int i = 0; i < poolSize; i++) {
                Connection connection = DriverManager.getConnection(url, properties);
                freeConnections.add(connection);
            }
        } catch (SQLException | ClassNotFoundException e) {
            log.error("Can not create ConnectionPool", e);
            throw new RuntimeException("Can not create ConnectionPool", e);
        }
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = freeConnections.take();
            usedConnections.add(connection);
        } catch (InterruptedException e) {
            log.error("Can not get Connection", e);
            Thread.currentThread().interrupt();
        }
        return connection;
    }

    public void releaseConnection(Connection connection) throws SQLException {
        if (connection != null) {
            connection.setAutoCommit(true);
            usedConnections.remove(connection);
            freeConnections.add(connection);
        }
    }

    public void destroyPool() throws SQLException {
        for (Connection connection : freeConnections) {
            connection.close();
        }
        for (Connection connection : usedConnections) {
            connection.close();
        }
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            DriverManager.deregisterDriver(drivers.nextElement());
        }

    }
}
