package DbHelper;

import lombok.SneakyThrows;
import org.testng.Assert;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class SqlQueryClickHouse {


    @SneakyThrows
    public String getValue(String SqlQuery, String columnLabel) {

        String pValue = null;
        ClickHouseConnect chc = null;
        PreparedStatement pst = null;

        try {
            chc = new ClickHouseConnect();
            pst = chc.getConnection().prepareStatement(SqlQuery);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                pValue = rs.getString(columnLabel);
            }
            rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            pst.getConnection().close();
            chc.getConnection().close();

        }
        return pValue;
    }

}

