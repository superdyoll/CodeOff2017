package sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Lloyd on 24/02/2016.
 */
public interface SQLModel {

    /**
     * Will return a connection to the previously specified database
     *
     * @return Connection
     * @throws SQLException
     */
    public Connection getConnection() throws Exception;

    /**
     * Will return a prepared statement given an sql statement to a given database
     *
     * @param sql
     * @return Prepared statement
     * @throws SQLException
     */
    public PreparedStatement getPreparedStatement(String sql) throws Exception;

    public void close() throws Exception;
}
