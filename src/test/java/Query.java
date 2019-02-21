import org.apache.kylin.jdbc.Driver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
public class Query {
    public static void main(String[] args) throws Exception {
        Driver driver = (Driver) Class.forName("org.apache.kylin.jdbc.Driver").newInstance();
        Properties info = new Properties();
        info.put("user", "ADMIN");
        info.put("password", "KYLIN");
        Connection conn = driver.connect("jdbc:kylin://ip:port/projectName", info);
        String sqlStr = "select distinct userid from dbName.sheetName where day_time <? limit 10000";
        PreparedStatement ps = conn.prepareStatement(sqlStr);
        ps.setString(1, "2018-02-01");
        ResultSet resultSet = ps.executeQuery();
        List uidList = new ArrayList();
        while (resultSet.next()) {
            uidList.add(resultSet.getString(1));
        }
//        uidList.forEach(uid -> System.out.println(uid));
    }
}
