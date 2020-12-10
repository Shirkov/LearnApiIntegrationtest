package DbHelper;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class ClickHouseConnect {

    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;

    static {
        config.setJdbcUrl("jdbc:clickhouse://localhost");
        config.setUsername("default");
        config.setPassword("");
        ds = new HikariDataSource(config);
    }

    public  Connection getConnection() throws SQLException {
        return ds.getConnection();
    }


}
