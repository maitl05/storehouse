package maitl.model.common;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;

public class ConnectionPool {
    private static BasicDataSource basicDataSource = new BasicDataSource();
    static {
        basicDataSource.setUsername("maitl");
        basicDataSource.setPassword("java123");
        basicDataSource.setUrl("jdbc:oracle:thin:@localhost:1521:xe");
        basicDataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
        basicDataSource.setMaxTotal(7);

    }
    public static Connection getConnection() throws SQLException{
        Locale.setDefault(Locale.US);
        return basicDataSource.getConnection();
    }
}
