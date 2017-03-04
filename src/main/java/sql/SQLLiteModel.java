package sql;

import org.sqlite.SQLiteConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Lloyd on 24/02/2016.
 */
public class SQLLiteModel implements SQLModel {

    private String flightDataName;
    private Connection connection;

    /**
     * Make a new model for a given flightData
     *
     * @param flightDataName
     */
    public SQLLiteModel(String flightDataName) {
        this.flightDataName = flightDataName;
        System.out.println("from sqlite model");
        System.out.println(flightDataName);
    }

    /**
     * Return connection
     *
     * @return
     * @throws SQLException
     */
    public Connection getConnection() throws Exception {
        if (connection == null) {
            connection = createConnection();
        } else if (connection.isClosed()) {
            connection = createConnection();
        }
        return connection;
    }

    private Connection createConnection() throws Exception {
        SQLiteConfig databaseConfig = new SQLiteConfig();

        databaseConfig.setPragma(SQLiteConfig.Pragma.JOURNAL_MODE, "WAL");
        databaseConfig.setPragma(SQLiteConfig.Pragma.SYNCHRONOUS, "NORMAL");
        databaseConfig.setPragma(SQLiteConfig.Pragma.CACHE_SIZE, "10000");


        Class.forName("org.sqlite.JDBC");
        return DriverManager.getConnection("jdbc:sqlite:_data/" + flightDataName + ".db", databaseConfig.toProperties());
    }

    /**
     * Return prepared statement
     *
     * @param sql
     * @return
     * @throws SQLException
     */
    public PreparedStatement getPreparedStatement(String sql) throws Exception {
        return getConnection().prepareStatement(sql);
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }

}
